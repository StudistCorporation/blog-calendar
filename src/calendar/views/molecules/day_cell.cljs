(ns calendar.views.molecules.day-cell
  (:require [shadow.css :refer [css]]
            [re-frame.core :refer [dispatch subscribe]]
            ["@tabler/icons" :refer [IconPencil IconPlus]]
            [calendar.events :as events]
            [calendar.subs :as subs]))

(def $day
  (css {:border "1px solid rgba(140, 130, 115, 0.12)"}))

(def $day-wrap
  (css {:height "130px"
        :min-height "130px"
        :max-height "130px"
        :width "180px"
        :min-width "180px"
        :max-width "180px"
        :display "flex"
        :flex-flow "column nowrap"}))

(def $date
  (css {:padding "0.5em 0 0 0.5em"
        :color "rgba(156, 156, 156, 0.8)"}))

(def $other-day
  (css {:background "#2a2d2f"}))

(def $calendar-day
  (css {}
       [:hover
        {:background "#1d2021"}]
       ["&:focus-within"
        {:background "#1d2021"}]))

(def $day-detail
  (css {:width "100%"
        :flex-grow "1"
        :display "flex"
        :flex-flow "column nowrap"}))

(def $day-title
  (css {:margin "0 0.5em 0.5em 0.5em"
        :word-wrap "break-word"
        :overflow "hidden"
        :text-overflow "ellipsis"
        :max-height "3em"}))

(def $day-footer
  (css {:display "flex"
        :flex-flow "row nowrap"
        :margin-top "auto"
        :align-items "center"
        :padding "0 0.5em 0.5em 0.5em"}))

(def $day-calendar
  (css {}))

(def $edit-button
  (css {:margin-left "auto"
        :align-self "flex-end"
        :width "2em"
        :height "2em"}))

(def $plus-button
  (css {:width "100%"
        :flex-grow "1"
        :display "flex"
        :flex-flow "row nowrap"
        :padding-bottom "0.75em"}
       [:focus
        {:outline "none"
         :border "none"}]))

(def $plus-icon
  (css {:margin "auto"}))

(defn day-cell
  [calendar? date]
  [:td
   {:key (gensym)
    :class [$day (if calendar?
                   $calendar-day
                   $other-day)]}
   (let [day (.getDate date)]
     [:div
      {:class [$day-wrap]}
      [:header
       {:class [$date]}
       (str (when (= day 1) (str (inc (.getMonth date)) "月")) day "日")]
      (when calendar?
        (if-let [{:keys [title calendar-url post-url]} @(subscribe [::subs/day day])]
          [:div
           {:class [$day-detail]}
           [:p
            {:class [$day-title]
             ;; shadow-css throws these away for some reason
             :style {"display" "-webkit-box"
                     "-webkit-box-orient" "vertical"
                     "-webkit-line-clamp" "2"}}
            (if post-url
              [:a
               {:href post-url}
               title]
              title)]
           [:div
            {:class [$day-footer]}
            (when calendar-url
              [:p
               {:class [$day-calendar]}
               [:a
                {:href calendar-url}
                "カレンダー"]])
            [:button
             {:class [$edit-button]
              :title "投稿予定を編集"
              :on-click #(dispatch [::events/dialog-show day])}
             [:> IconPencil
              {:size 32
               :stroke 2}]]]]
          [:button
           {:class [$plus-button]
            :title "投稿予定を追加"
            :on-click #(dispatch [::events/dialog-show day])}
           [:> IconPlus
            {:class [$plus-icon]
             :size 32
             :stroke 3}]]))])])
