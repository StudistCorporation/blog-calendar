(ns calendar.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::current-route
 :-> :current-route)

(rf/reg-sub
 ::calendar
 :-> :calendar)

(rf/reg-sub
 ::filled-days
 (fn [{:keys [days]}]
   (count days)))

(rf/reg-sub
 ::total-days
 :<- [::calendar]
 (fn [{:keys [start end]}]
   (let [start-day (.getDate (js/Date. start))
         end-day (.getDate (js/Date. end))]
     (inc (- end-day start-day)))))

(rf/reg-sub
 ::day
 (fn [db [_ day]]
   (get-in db [:days day])))

(rf/reg-sub
 ::dialog-day
 :-> :dialog)

(rf/reg-sub
 ::jwt
 :-> :jwt)

(rf/reg-sub
 ::login-state
 :-> :login-state)
