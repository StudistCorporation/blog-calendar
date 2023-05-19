(ns calendar.routes.web
  #?(:clj (:require [calendar.handlers.html :as html]
                    [calendar.events :as-alias events])
     :cljs (:require [re-frame.core :as rf]
                     [calendar.events :as events]
                     [calendar.views.pages.calendar :as calendar]
                     ; [calendar.views.pages.dashboard :as dashboard]
                     ; [calendar.views.pages.invite :as invite]
                     [calendar.views.pages.login :as login]
                     ; [calendar.views.pages.register :as register]
                     ,)))

(def routes
  ["/"
   #?(:clj {:get {:handler html/handler}})
   ["" {:name ::current
        #?@(:cljs [:view #'calendar/view
                   :controllers [{:start #(rf/dispatch [::events/fetch-calendar])}]])}]
   ["dashboard" {:name ::dashboad
                 ; #?@(:cljs [:view #'dashboard/view])
                 ,}]
   ["login" {:name ::login
             #?@(:cljs [:view #'login/view])}]
   ["register" {:name ::register
                ; #?@(:cljs [:view #'register/view])
                ,}]])
