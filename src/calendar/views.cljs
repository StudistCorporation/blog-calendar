(ns calendar.views
  (:require [re-frame.core :as rf]
            [shadow.css :refer [css]]
            [calendar.subs :as subs]
            [calendar.views.atoms.git-link :as git-link]
            [calendar.views.organisms.dialog :as dialog]
            [calendar.views.organisms.header :as header]
            [calendar.views.pages.not-found :as not-found]))

(def $main
  (css {:align-items "center"
        :background "#181a1b"
        :color "#9c9c9c"
        :display "flex"
        :flex-flow "column nowrap"
        :font "normal normal 16px/1.5em sans-serif"
        :min-height "100vh"
        :padding-bottom "30px"}))

(defn main
  []
  (let [route @(rf/subscribe [::subs/current-route])
        opts {:params (:parameters route)}]
    [:div
     {:class [$main]}
     [header/view]
     (if route
       [(-> route :data :view) opts]
       [not-found/view opts])
     [dialog/view]
     [git-link/view]]))
