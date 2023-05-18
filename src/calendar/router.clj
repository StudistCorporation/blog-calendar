(ns calendar.router
  (:require [reitit.ring :as ring]
            [calendar.routes :refer [routes]]))

(def handler
  (ring/ring-handler
   (ring/router routes)
   (ring/routes
    (ring/create-resource-handler {:path "" :root ""})
    (ring/create-default-handler))))
