(ns calendar.views.organisms.header
  (:require [re-frame.core :as rf]
            [reitit.frontend.easy :refer [href]]
            [shadow.css :refer [css]]
            [calendar.routes.web :as-alias web]
            [calendar.subs :as subs]
            [calendar.views.atoms.user-icon :as user-icon]))

(def $header
  (css {:align-items "center"
        :display "flex"
        :flex-flow "row nowrap"
        :justify-content "space-between"
        :margin "15px auto"
        :width "1260px"}))

(def $title
  (css {:font-size "1.3em"
        :line-height "1.5em"}))

(defn view
  []
  [:header
   {:class [$header]}
   [:h1
    {:class [$title]}
    [:a
     {:href (href ::web/home)}
     "ブログ​カレンダー"]]
   (let [{:keys [display-name email-md5]}
         @(rf/subscribe [::subs/current-user])]
     [:nav
      [user-icon/view email-md5]
      [:span
       (str display-name)]])])
