(ns calendar.views.atoms.user-icon
  (:require [shadow.css :refer [css]]))

(def $icon
  (css {:border-radius "0.7em"
        :display "inline"
        :height "1.4em"
        :margin "0 0.5em"
        :vertical-align "top"}))

(defn view
  [email-md5]
  [:img
   {:class [$icon]
    :src (str "https://www.gravatar.com/avatar/" email-md5 "?d=retro")}])
