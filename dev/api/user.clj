(ns user
  (:require [clojure.repl :refer [doc source]]
            [clojure.tools.namespace.repl :refer [refresh set-refresh-dirs]]
            [mount.core :refer [defstate start stop]]))

(set-refresh-dirs "dev/api" "src")

(defn reload
  []
  (stop)
  (refresh :after 'mount.core/start))
