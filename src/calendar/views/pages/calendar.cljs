(ns calendar.views.pages.calendar
  (:require [re-frame.core :as rf]
            [shadow.css :refer [css]]
            [calendar.subs :as subs]
            [calendar.views.organisms.calendar :as calendar]))

(def $message
  (css {:margin "auto"
        :min-width "min-content"}))

(defn view
  [_]
  (let [calendar @(rf/subscribe [::subs/calendar])]
    (case calendar
      nil
      [:p
       {:class $message}
       "読み込み中..."]

      {}
      [:p
       {:class $message}
       "開催予定のカレンダーがありません。"]

      ;; else
      [calendar/view calendar])))
