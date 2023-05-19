(ns calendar.views.molecules.calendar-meta
  (:require [re-frame.core :as rf]
            [calendar.subs :as subs]))

(defn view
  []
  [:p
   {:class []}
   (let [filled @(rf/subscribe [::subs/filled-days])
         total @(rf/subscribe [::subs/total-days])]
     (str "参加人数 " filled "/" total))
   [:br]
   (str "作成者 " "ユーザー")])
