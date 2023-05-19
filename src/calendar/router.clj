(ns calendar.router
  (:require [muuntaja.core :as muuntaja]
            [reitit.coercion.malli :as coercion]
            [reitit.ring :as ring]
            [reitit.ring.coercion :refer [coerce-exceptions-middleware
                                          coerce-request-middleware
                                          coerce-response-middleware]]
            [reitit.ring.middleware.parameters :refer [parameters-middleware]]
            [reitit.ring.middleware.muuntaja :refer [format-negotiate-middleware
                                                     format-response-middleware
                                                     format-request-middleware]]
            [calendar.handlers.html :as html]
            [calendar.routes :refer [routes]]
            [calendar.wrappers.jwt :refer [wrap-jwt]]
            [calendar.wrappers.logging :refer [wrap-logging]]))

(def handler
  (ring/ring-handler
   (ring/router
    routes
    {:data
     {:muuntaja (muuntaja/create (merge muuntaja/default-options
                                        {:return :bytes}))
      :coercion (coercion/create {})
      :middleware
      [wrap-logging
       parameters-middleware
       format-negotiate-middleware
       format-response-middleware
       format-request-middleware
       coerce-exceptions-middleware
       coerce-response-middleware
       coerce-request-middleware
       wrap-jwt]}})
   (ring/routes
    (ring/create-resource-handler {:path "" :root ""})
    (ring/create-default-handler
     {:not-found (constantly {:status 404 :body html/file-body})
      :method-not-allowed (constantly {:status 405 :body html/file-body})}))
   {:inject-router? false}))
