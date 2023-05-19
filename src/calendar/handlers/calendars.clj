(ns calendar.handlers.calendars
  (:require [calendar.db.calendars :as db]))

(def dummy
  {:comment "梅雨を乗り切ろう！"
   :filled {}
   :start "2023/06/08"
   :end "2023/06/16"})

(defn active
  [_]
  (if-let [calendar (db/active)]
    {:status 200 :body dummy}
    {:status 404}))
