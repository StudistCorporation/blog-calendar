(ns calendar.events.dialog
  (:require [re-frame.core :as rf]
            [calendar.effects :as fx]
            [calendar.interop :refer [form->map]]))

(rf/reg-event-fx
 ::show
 (fn [{db :db} [_ day]]
   {:db (assoc db :dialog day)
    ::fx/dialog [:show]}))

(rf/reg-event-fx
 ::close
 (fn [_ _]
   {::fx/dialog [:close]}))

(rf/reg-event-fx
 ::click
 (fn [_ [_ e]]
   {::fx/dialog [:click e]}))

(rf/reg-event-fx
 ::submit
 (fn [{db :db} [_ day e]]
   (when-let [form (.-target e)]
     (let [{:keys [title post calendar]} (form->map form)]
       {:db (assoc-in db [:days day] {:title title
                                      :post-url (not-empty post)
                                      :calendar-url (not-empty calendar)})
        ::fx/dialog [:reset e]}))))
