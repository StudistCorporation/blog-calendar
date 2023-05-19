(ns calendar.handlers.session
  (:require [clojure.tools.logging :as log]
            [calendar.db.users :as db]))

(defn handler
  [{{:keys [role] id :sub} :identity}]
  (if (not= role "authenticated")
    {:status 401}
    (if-let [user (db/by-auth-id id)]
      {:status 200 :body "\"hi\""}
      {:status 401})))
