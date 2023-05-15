(ns calendar.router
  (:require [reitit.ring :as ring]))

(def handler
  (ring/ring-handler
   (ring/router
    [["/api" (constantly {:status 200 :body "Hello, world"})]])
   (ring/routes
    (ring/create-resource-handler {:path "/"})
    (ring/create-default-handler))))
