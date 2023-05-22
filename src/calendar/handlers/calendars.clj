(ns calendar.handlers.calendars
  (:require [ring.util.http-response :refer [ok not-found]]
            [clojure.tools.logging :as log]
            [calendar.db.calendars :as cal-db]
            [calendar.db.users :as user-db]))

(defn dummy-convert-cal
  [{:calendars/keys [title description created-by first-day last-day]}]
  {:title title
   :description description
   :created-by created-by
   :start first-day
   :end last-day})

(defn dummy-convert-user
  [{:users/keys [id display-name email-md5]}]
  {:id id
   :display-name display-name
   :email-md5 email-md5})

(defn active
  [_]
  (if-let [calendar (cal-db/active)]
    (let [days []
          users (user-db/many-by-id [(:calendars/created-by calendar)])]
      (ok {:calendar (dummy-convert-cal calendar)
           :days days
           :users (map dummy-convert-user users)}))
    (not-found)))
