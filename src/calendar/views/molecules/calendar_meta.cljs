(ns calendar.views.molecules.calendar-meta
  (:require [re-frame.core :as rf]
            [shadow.css :refer [css]]
            [calendar.subs :as subs]
            [calendar.views.atoms.user-icon :as user-icon]))

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
      [user-icon/view email-md5]
      [:span
       (str display-name)]])])
