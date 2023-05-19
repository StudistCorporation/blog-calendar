(ns calendar.routes.api
  #?(:clj (:require [calendar.handlers.calendars :as calendars]
                    [calendar.handlers.session :as session])))

(def routes
  ["/api"
   ["/article/:id" {:name ::article}]
   ["/calendar"
    ["/" {:name ::active-calendar
          :get {#?@(:clj [:handler calendars/active])}}]
    ["/:id" {:name ::calendar}]]
   ["/invite"
    ["/" {:name ::invite}]
    ["/:id" {:name ::accept}]]
   ["/session" {:name ::session
                :get {:scopes {:user-status :active}
                      #?@(:clj [:handler session/handler])}}]])
