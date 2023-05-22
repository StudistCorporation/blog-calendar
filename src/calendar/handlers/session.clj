(ns calendar.handlers.session
  (:require [clojure.tools.logging :as log]
            [ring.util.http-response :refer [ok unauthorized]]
            [calendar.db.users :as db]
            [calendar.lang :refer [...]]))

(defn handler
  [{{:keys [role] id :sub} :identity}]
  (if (not= role "authenticated")
    (unauthorized)
    (if-let [{:users/keys [display-name email-md5 status]}
             (db/by-auth-id id)]
      (ok (... display-name email-md5 status))
      (unauthorized))))
