(ns calendar.views.molecules.calendar-meta
  (:require [re-frame.core :as rf]
            [shadow.css :refer [css]]
            [calendar.subs :as subs]))

(def $icon
  (css {:border-radius "0.7em"
        :display "inline"
        :height "1.4em"
        :margin "0 0.5em"
        :vertical-align "top"}))

(defn view
  []
  [:div
   {:class []}
   (let [filled @(rf/subscribe [::subs/filled-days])
         total @(rf/subscribe [::subs/total-days])]
     [:p
      (str "参加人数 " filled "/" total)])
   (let [{:keys [display-name email-md5]}
         @(rf/subscribe [::subs/calendar-author])]
     [:p
      [:span
       "作成者"]
      [:img
       {:class [$icon]
        :src (str "https://www.gravatar.com/avatar/" email-md5 "?d=retro")}]
      [:span
       (str display-name)]])])
