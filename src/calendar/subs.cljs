(ns calendar.subs
  (:require [re-frame.core :as rf]))

(rf/reg-sub
 ::current-route
 (fn [db]
   (:current-route db)))

(rf/reg-sub
 ::calendar
 (fn [db]
   (:calendar db)))

(rf/reg-sub
 ::day
 (fn [db [_ day]]
   (get-in db [:days day])))

(rf/reg-sub
 ::dialog-day
 (fn [db _]
   (:dialog db)))
