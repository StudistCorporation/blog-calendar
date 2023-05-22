(ns calendar.views.pages.calendar
  (:require [re-frame.core :as rf]
            [shadow.css :refer [css]]
            [calendar.subs :as subs]
            [calendar.views.molecules.calendar-meta :as calendar-meta]
            [calendar.views.organisms.calendar :as calendar]))

(def $wrap
  (css {:margin "auto"}))

(def $message
  (css {:min-width "min-content"
        :text-align "center"}))

(def $header
  (css {:background "#1d2021"
        :border-top "1px solid rgba(140, 130, 115, 0.12)"
        :border-bottom "1px solid rgba(140, 130, 115, 0.12)"
        :margin-bottom "15px"
        :padding "15px 0"
        :text-align "left"
        :width "100%"}))

(def $header-padding
  (css {:max-width "1260px"}))

(def $title
  (css {:font-size "1.5em"
        :line-height "1.5em"
        :margin-bottom "10px"}))

(def $description
  (css {:margin-bottom "15px"}))

(defn view
  [_]
  (let [{:keys [title description] :as calendar}
        @(rf/subscribe [::subs/calendar])]
    (case calendar
      nil
      [:p
       {:class [$wrap $message]}
       "読み込み中..."]

      {}
      [:p
       {:class [$wrap $message]}
       "直近開催予定のカレンダーがありません。"
       [:br]
       "またきてください！"]

      ;; else
      [:div
       {:class [(css :w-full)]}
       [:header
        {:class [$header]}
        [:div
         {:class [$wrap $header-padding]}
         [:h1
          {:class [$title]}
          (str (or title "\u202f"))]
         [calendar-meta/view]]]
       (when description
         [:div
          {:class [$wrap $header-padding $description]}
          [:p
           (str description)]])
       [calendar/view calendar]])))
