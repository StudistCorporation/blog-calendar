(ns calendar.effects
  (:require [re-frame.core :as rf]
            [reitit.frontend.easy :as easy]
            [cognitect.transit :as t]
            ["@supabase/supabase-js" :as supabase]))

(rf/reg-fx
 ::dialog
 (fn [[mode e]]
   (let [dialog (js/document.getElementById "dialog")
         form (js/document.getElementById "form")]
     (case mode
       :show
       (.showModal dialog)

       :close
       (do
         (.close dialog)
         (.reset form))

       :reset
       (some-> e .-target .reset)

       ;; else
       (when-let [target (.-target e)]
         (if (= "DIALOG" (.-nodeName target))
           (do
             (.close dialog)
             (.reset form))
           (.stopPropagation e)))))))

(def client
  (supabase/createClient
   "https://niahyllxmxykxptmlcez.supabase.co"
   "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Im5pYWh5bGx4bXh5a3hwdG1sY2V6Iiwicm9sZSI6ImFub24iLCJpYXQiOjE2ODQxMzA4MTEsImV4cCI6MTk5OTcwNjgxMX0.s0z-zekkrl0decTEROkTRqqqS1y_ryZhY-RAkiWtub4"))

(rf/reg-fx
 ::supabase
 (fn [{:keys [action on-success on-error] :as opts}]
   (let [callback
         (fn [result]
           (if-let [error (.-error result)]
             (when (fn? on-error)
               (on-error {:error error}))
             (when (fn? on-success)
               (on-success (js->clj result :keywordize-keys true)))))]
     (case action
       :login
       (-> client (.-auth) (.signInWithPassword
                            (clj->js {:email (:email opts)
                                      :password (:password opts)}))
           (.then callback))

       :logout
       nil ;; TODO

       :refresh
       (-> client (.-auth) (.getSession)
           (.then callback))))))

(rf/reg-fx
 ::push-state
 (fn [route]
   (apply easy/push-state route)))

(rf/reg-fx
 ::pop-state
 (fn [_]
   (js/window.history.back)))
