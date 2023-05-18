(ns calendar.routes
  (:require [calendar.routes.api :as api]
            [calendar.routes.web :as web]))

(def routes
  [api/routes
   web/routes])
