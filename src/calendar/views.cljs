(ns calendar.views
  (:require [re-frame.core :as rf]
            [shadow.css :refer [css]]
            [calendar.subs :as subs]
            [calendar.views.atoms.git-link :as git-link]
            [calendar.views.organisms.dialog :as dialog]
            [calendar.views.pages.not-found :as not-found]))

(def $main
  (css {:background "#181a1b"
        :color "#9c9c9c"
        :font "normal normal 16px/1.5em sans-serif"
        :height "100vh"
        :display "flex"
        :flex-flow "row no-wrap"
        :align-items "center"}))

(defn main
  []
  (let [route @(rf/subscribe [::subs/current-route])
        opts {:params (:parameters route)}]
    [:div
     {:class [$main]}
     (if route
       [(-> route :data :view) opts]
       [not-found/view opts])
     [dialog/view]
     [git-link/view]]))
