(ns calendar.handlers.html
  (:require [clojure.java.io :as io]))

(def file-body
  (slurp (io/resource "index.html")))

(defn handler
  [_]
  {:status 200
   :body file-body
   :headers {:content-type "text/html"}})
