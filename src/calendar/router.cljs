(ns calendar.router
  (:require [re-frame.core :as rf]
            [reitit.frontend]
            [reitit.frontend.easy :as easy]
            [calendar.events.routing :as routing]
            [calendar.routes :refer [routes]]))

(def router
  (reitit.frontend/router routes))

(defn on-navigate
  [new-match]
  (when new-match
    (rf/dispatch [::routing/navigated new-match])))

(defn start-router
  "Used in the actual browser, using the browser's HTML5 History API"
  []
  (easy/start!
   router
   on-navigate
   {:use-fragment false}))
