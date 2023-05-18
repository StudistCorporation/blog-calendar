(ns calendar.core
  (:require [aleph.http :as http]
            [clojure.tools.logging :as log]
            [mount.core :refer [defstate start stop]]
            [calendar.router :as router])
  (:import [java.io
            Closeable])
  (:gen-class))

(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defstate http-server
  :start
  (let [options {:port 3000
                 :compression true}]
    (log/debug (str "API server running at :" (:port options)))
    (http/start-server router/handler options))
  :stop
  (.close ^Closeable http-server))

(defn -main
  [& _args]
  (start))
