(ns calendar.db.core
  (:require [clojure.string :as str]
            [clojure.tools.logging :as log]
            [mount.core :refer [defstate]]
            [hikari-cp.core :refer [make-datasource close-datasource]]
            [honey.sql :as sql]
            [next.jdbc]
            [next.jdbc.result-set :as rs]))

(defstate datasource
  :start
  (make-datasource {:jdbc-url (str "jdbc:postgresql://"
                                   (System/getenv "POSTGRES__HOST")
                                   "/postgres?currentSchema=calendar")
                    :username "postgres"
                    :password (str (System/getenv "POSTGRES__PASS"))})
  :stop
  (close-datasource datasource))

(defn convert_-
  [input]
  (str/replace input "_" "-"))

(defn as-kebab-maps
  "->kebab-case inserts a - between letters and numbers, which is undersirable
   eg it converts md5 to md-5"
  [rs opts]
  (rs/as-modified-maps rs (assoc opts
                                 :qualifier-fn convert_-
                                 :label-fn convert_-)))

(defmacro defquery
  [name & decl]
  (loop [meta []
         stage ::meta
         items decl]
    (case stage
      ::meta
      (let [head (first items)]
        (cond
          ;; docstring
          (string? head) (recur [head] ::meta (rest items))
          ;; attr-map
          (map? head) (recur (conj meta head) ::main (rest items))
          :else (recur meta ::main items)))

      ::main
      (let [args (first items)
            body (rest items)]
        `(defn ~name
           ~@meta
           ~(let [dummy-args# (mapv gensym args)]
              `(~dummy-args#
                (~name calendar.db.core/datasource ~@dummy-args#)))
           ([datasource# ~@args]
            (try
              (let [start# (System/nanoTime)
                    sql-dsl# (do ~@body)
                    text# (sql/format sql-dsl#)
                    result# (next.jdbc/execute! datasource# text#)]
                (log/debug (format "%.3fms" (/ (- (System/nanoTime) start#) 1000000.0))
                           (pr-str text#))
                (if (= 1 (:limit sql-dsl#))
                  (first result#)
                  result#)))))))))
