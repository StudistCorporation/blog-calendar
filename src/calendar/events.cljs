(ns calendar.events
  (:require [re-frame.core :as rf]
            [reitit.frontend.controllers :as router]
            [calendar.effects :as fx]))

(rf/reg-event-fx
 ::initialize-db
 (fn [{:keys [persisted]}]
   {:db {}}))

(rf/reg-event-db
 ::navigated
 (fn [db [_ new-match]]
   (let [old-match (:current-route db)
         controllers (router/apply-controllers
                      (:controllers old-match)
                      new-match)]
     (assoc db :current-route
            (assoc new-match :controllers controllers)))))

(rf/reg-event-fx
 ::dialog-show
 (fn [{db :db} [_ day]]
   {:db (assoc db :dialog day)
    ::fx/dialog [:show]}))

(rf/reg-event-fx
 ::dialog-close
 (fn [_ _]
   {::fx/dialog [:close]}))

(rf/reg-event-fx
 ::dialog-click
 (fn [_ [_ e]]
   {::fx/dialog [:click e]}))

(rf/reg-event-fx
 ::dialog-submit
 (fn [{db :db} [_ day e]]
   (when-let [form (.-target e)]
     (let [{:keys [title post calendar]} (-> form
                                             (js/FormData.)
                                             (js/Object.fromEntries)
                                             (js->clj :keywordize-keys true))]
       {:db (assoc-in db [:days day] {:title title
                                      :post-url (not-empty post)
                                      :calendar-url (not-empty calendar)})
        ::fx/dialog [:reset e]}))))

(rf/reg-event-db
 ::fetch-calendar
 (fn [db _]
   (assoc db :calendar {:comment "梅雨を乗り切ろう！"
                        :filled {}
                        :start "2023/06/08"
                        :end "2023/06/16"})))

(rf/reg-event-fx
 ::clear-day
 (fn [{db :db} [_ day]]
   {:db (update db :days dissoc day)
    ::fx/dialog [:close]}))
