(ns calendar.events
  (:require [re-frame.core :as rf]
            [reitit.frontend.controllers :as router]
            [reitit.frontend.easy :refer [href]]
            [calendar.effects :as fx]
            [calendar.events.http :as http]
            [calendar.interop :refer [form->map]]
            [calendar.routes.api :as-alias api]
            [calendar.routes.web :as-alias web]))

(rf/reg-event-fx
 ::initialize
 (fn [_]
   {:db {}
    :dispatch [::refresh-authn]}))

(rf/reg-event-fx
 ::navigate-to
 (fn [_ [_ & route]]
   {::fx/push-state route}))

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
 ::login-submit
 (fn [{db :db} [_ ^js/Event event]]
   (when-let [form (.-target event)]
     (let [{:keys [email password]} (form->map form)]
       {:db (assoc db :login-state :in-progress)
        ::fx/supabase {:action :login
                       :email email
                       :password password
                       :on-error #(rf/dispatch [::login-error %])
                       :on-success #(rf/dispatch [::commit-authn %])}}))))

(rf/reg-event-fx
 ::login-success
 (fn [{db :db} _]
   {:db (assoc db :login-state :success)
    :dispatch [::navigate-to ::web/dashboard]}))

(rf/reg-event-db
 ::login-error
 (fn [db _]
   (assoc db :login-state :error)))

(rf/reg-event-fx
 ::fetch-calendar
 (fn [_ _]
   {:dispatch [::http/load {:path (href ::api/active-calendar)
                            :on-success ::commit-calendar
                            :on-error ::clear-calendar}]}))

(rf/reg-event-db
 ::commit-calendar
 (fn [db [_ {:keys [calendar _days users] :as data}]]
   (js/console.log (str data))
   (let [old-users (:users db)
         new-users (reduce
                    (fn [aggr {:keys [id] :as user}]
                      (assoc aggr id user))
                    old-users
                    users)]
     (-> db
         (assoc :calendar calendar)
         (assoc :days {}) ;; TODO
         (assoc :users new-users)))))

(rf/reg-event-db
 ::clear-calendar
 (fn [db _]
   (assoc db :calendar {})))

(rf/reg-event-fx
 ::clear-day
 (fn [{db :db} [_ day]]
   {:db (update db :days dissoc day)
    ::fx/dialog [:close]}))

(rf/reg-event-fx
 ::refresh-authn
 (fn [_ _]
   {::fx/supabase {:action :refresh
                   :on-success #(rf/dispatch [::commit-authn %])}}))

(rf/reg-event-fx
 ::commit-authn
 (fn [{db :db} [_ {{:keys [session]} :data}]]
   {:db
    (-> db
        (assoc :jwt (:access_token session))
        (assoc :current-user (select-keys (:user session) [:email :id])))
    :dispatch [::refresh-session]}))

(rf/reg-event-fx
 ::refresh-session
 (fn [_ _]
   {:dispatch [::http/load {:path (href ::api/session)
                            :on-success ::commit-session
                            :on-error ::reset-session}]}))

(rf/reg-event-fx
 ::commit-session
 (fn [{{:keys [login-state] :as db} :db} [_ data]]
   {:db (update db :current-user #(merge % data))
    :dispatch (when (= login-state :in-progress)
                [::login-success])}))

(rf/reg-event-fx
 ::reset-session
 (fn [{{:keys [login-state] :as db} :db} _]
   {::fx/supabase {:action :logout}
    :dispatch (when (= login-state :in-progress)
                [::login-error])
    :db (-> db
            (dissoc :jwt)
            (dissoc :current-user))}))
