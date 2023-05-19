(ns calendar.events
  (:require [re-frame.core :as rf]
            [reitit.frontend.controllers :as router]
            [calendar.effects :as fx]
            [calendar.interop :refer [form->map]]
            [calendar.routes.web :as-alias web]))

(rf/reg-event-fx
 ::initialize
 (fn [_]
   {:db {}
    :dispatch [::refresh-session]}))

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
     (let [{:keys [title post calendar]} (form->map form)]
       {:db (assoc-in db [:days day] {:title title
                                      :post-url (not-empty post)
                                      :calendar-url (not-empty calendar)})
        ::fx/dialog [:reset e]}))))

(rf/reg-event-fx
 ::login-submit
 (fn [{db :db} [_ ^js/Event event]]
   (when-let [form (.-target event)]
     (let [{:keys [email password]} (form->map form)]
       {:db (assoc db :login-state :in-progress)
        ::fx/supabase {:action :login
                       :email email
                       :password password
                       :on-error #(rf/dispatch [::login-error %])
                       :on-success #(rf/dispatch [::login-success %])}}))))

(rf/reg-event-fx
 ::login-success
 (fn [{db :db} [_ result]]
   {:db (assoc db :login-state :success)
    :dispatch [::commit-session result]
    ::fx/push-state [::web/current]}))

(rf/reg-event-db
 ::login-error
 (fn [db _]
   (assoc db :login-state :error)))

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

(rf/reg-event-fx
 ::refresh-session
 (fn [_ _]
   {::fx/supabase {:action :refresh
                   :on-success #(rf/dispatch [::commit-session %])}}))

(rf/reg-event-fx
 ::commit-session
 (fn [{db :db} [_ {{:keys [session]} :data}]]
   {:db ;; TODO: update calendar user data too
    (-> db
        (assoc :jwt (:access_token session))
        (assoc :user-id (get-in session [:user :id]))
        (assoc :user-email (get-in session [:user :email])))}))
