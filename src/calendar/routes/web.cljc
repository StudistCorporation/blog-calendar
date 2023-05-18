(ns calendar.routes.web
  #?(:clj (:require [calendar.handlers.html :as html])
     :cljs (:require
                     ; [calendar.views.pages.calendar :as calendar]
                     ; [calendar.views.pages.dashboard :as dashboard]
                     ; [calendar.views.pages.invite :as invite]
                     ; [calendar.views.pages.login :as login]
                     ; [calendar.views.pages.register :as register]
                     ,)))

(def routes
  ["/"
   #?(:clj {:get {:handler html/handler}})
   ["" {:name ::home}]
   ["archive" {:name ::archive}]
   ["calendar/:id" {:name ::calendar
                    ; #?@(:cljs [:view #'calendar/view])
                    ,}]
   ["dashboard" {:name ::dashboad
                 ; #?@(:cljs [:view #'dashboard/view])
                 ,}]
   ["login" {:name ::login
             ; #?@(:cljs [:view #'login/view])
             ,}]
   ["register" {:name ::register
                ; #?@(:cljs [:view #'register/view])
                ,}]])
