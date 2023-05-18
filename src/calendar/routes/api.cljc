(ns calendar.routes.api)

(def routes
  ["/api"
   ["/article/:id" {:name ::article}]
   ["/calendar/:id" {:name ::calendar}]
   ["/invite"
    ["/" {:name ::invite}]
    ["/:id" {:name ::accept}]]])
