(ns user
  (:require [clojure.repl :refer [doc source]]
            [clojure.tools.namespace.repl :refer [refresh set-refresh-dirs]]
            [camel-snake-kebab.core :refer [->kebab-case-string]]
            [mount.core :refer [defstate start stop]])
  (:import [java.time
            ZonedDateTime
            ZoneId]
           [java.time.format
            DateTimeFormatter]))

(set-refresh-dirs "dev/api" "src")

(defn reload
  []
  (stop)
  (refresh :after 'mount.core/start))

(defn now
  []
  (let [utc (ZoneId/of "UTC")
        formatter (DateTimeFormatter/ofPattern "uuuuMMddHHmmss")
        timestamp (ZonedDateTime/now utc)]
    (.format formatter timestamp)))

(defn create-migration
  [name]
  (let [base (str (now) "-" (->kebab-case-string name))
        folder "config/migrations/"
        up-name (str folder base ".up.sql")
        down-name (str folder base ".down.sql")
        command "chown 1000:1000 "]
    (spit up-name (str "-- " up-name "\n"))
    (spit down-name (str "-- " down-name "\n"))
    (.exec (Runtime/getRuntime) (str command up-name))
    (.exec (Runtime/getRuntime) (str command down-name))))
