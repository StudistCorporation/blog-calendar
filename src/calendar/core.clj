(ns calendar.core
  (:require [aleph.http :as http]
            [mount.core :refer [defstate start stop]])
  (:import [java.io
            Closeable])
  (:gen-class))

(defstate http-server
  :start
  (http/start-server
   (constantly {:status 200 :body "Hello, world"})
   {:port 3000
    :compression true})
  :stop
  (.close ^Closeable http-server))

(defn -main
  [& _args]
  (start))
