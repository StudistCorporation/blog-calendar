(ns calendar.events
  (:require [re-frame.core :as rf]
            [reitit.frontend.controllers :as router]
            [calendar.effects :as fx]))

(rf/reg-event-fx
 ::initialize-db
 [(rf/inject-cofx ::fx/persisted)]
 (fn [{:keys [persisted]}]
   {:db {:days persisted}}))

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
 [fx/persist]
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

(rf/reg-event-fx
 ::clear-day
 [fx/persist]
 (fn [{db :db} [_ day]]
   {:db (update db :days dissoc day)
    ::fx/dialog [:close]}))
