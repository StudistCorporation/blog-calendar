(ns calendar.routes.api
  #?(:clj (:require [calendar.handlers.session :as session])))

(def routes
  ["/api"
   ["/article/:id" {:name ::article}]
   ["/calendar/:id" {:name ::calendar}]
   ["/invite"
    ["/" {:name ::invite}]
    ["/:id" {:name ::accept}]]
   ["/session" {:name ::session
                :get {:scopes {:user-status :active}
                      #?@(:clj [:handler session/handler])}}]])
