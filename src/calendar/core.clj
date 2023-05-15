(ns calendar.core
  (:require [aleph.http :as http]
            [mount.core :refer [defstate start stop]]
            [calendar.router :as router])
  (:import [java.io
            Closeable])
  (:gen-class))

(defstate http-server
  :start
  (http/start-server
   router/handler
   {:port 3000
    :compression true})
  :stop
  (.close ^Closeable http-server))

(defn -main
  [& _args]
  (start))
