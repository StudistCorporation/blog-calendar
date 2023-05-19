(ns calendar.handlers.calendars
  (:require [ring.util.http-response :refer [ok not-found]]
            [clojure.tools.logging :as log]
            [calendar.db.calendars :as cal-db]
            [calendar.db.users :as user-db]))

(defn dummy-convert-cal
  [{:calendars/keys [title description created_by first_day last_day]}]
  {:title title
   :description description
   :created-by created_by
   :start first_day
   :end last_day})

(defn dummy-convert-user
  [{:users/keys [id display_name email_md5]}]
  {:id id
   :display-name display_name
   :email-md5 email_md5})

(defn active
  [_]
  (if-let [calendar (cal-db/active)]
    (let [days []
          users (user-db/many-by-id [(:calendars/created_by calendar)])]
      (ok {:calendar (dummy-convert-cal calendar)
           :days days
           :users (map dummy-convert-user users)}))
    (not-found)))
