(ns calendar.router
  (:require [reitit.ring :as ring]
            [calendar.handlers.html :as html]
            [calendar.routes :refer [routes]]))

(def handler
  (ring/ring-handler
   (ring/router routes)
   (ring/routes
    (ring/create-resource-handler {:path "" :root ""})
    (ring/create-default-handler
     {:not-found (constantly {:status 404 :body html/file-body})
      :method-not-allowed (constantly {:status 405 :body html/file-body})}))))
