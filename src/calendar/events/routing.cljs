(ns calendar.events.routing
  (:require [re-frame.core :as rf]
            [reitit.frontend.controllers :as router]
            [calendar.effects :as fx]))

(rf/reg-event-fx
 ::navigate-to
 (fn [_ [_ & route]]
   {::fx/push-state route}))

(rf/reg-event-fx
 ::teleport-to
 (fn [_ [_ & route]]
   {::fx/replace-state route}))

(rf/reg-event-db
 ::navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (router/apply-controllers
                      (:controllers old-match)
                      new-match)]
     (assoc db :current-route
            (assoc new-match :controllers controllers)))))
