(ns calendar.router
  (:require [clojure.string :as str]
            [re-frame.core :as rf]
            [reitit.core :refer [match-by-path]]
            [reitit.frontend]
            [reitit.frontend.easy :as easy]
            [calendar.events.routing :as routing]
            [calendar.routes :refer [routes]]))

(def router
  (reitit.frontend/router routes))

(defn on-navigate
  [new-match]
  (if new-match
    (rf/dispatch [::routing/navigated new-match])
    (let [[& path] (.-pathname js/window.location)
          head (str/join "" (butlast path))]
      ;; if the current request path ends in a / and it's not just /
      (when (and (= (last path) "/")
                 (> (count head) 1))
        ;; then check if there's a route defined without the trailing slash
        (when-let [{{:keys [name]} :data}
                   (match-by-path router head)]
          ;; and "replace" location to that route if so
          (rf/dispatch [::routing/teleport-to name]))))))

(defn start-router
  "Used in the actual browser, using the browser's HTML5 History API"
  []
  (easy/start!
   router
   on-navigate
   {:use-fragment false}))
