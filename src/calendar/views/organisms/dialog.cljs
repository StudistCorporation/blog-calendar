(ns calendar.views.organisms.dialog
  (:require [shadow.css :refer [css]]
            [re-frame.core :refer [dispatch subscribe]]
            [calendar.events :as events]
            [calendar.subs :as subs]))

(def $dialog
  (css {:background "#181a1b"
        :color "#9c9c9b"
        :border "1px solid rgba(140, 130, 115, 0.12)"
        :padding "0"}
       ["&::backdrop"
        {:background "rgba(0, 0, 0, 0.5)"}]))

(def $dialog-block
  (css {:padding "0.5em 1em"
        :box-sizing "border-box"
        :width "400px"}))

(def $dialog-head
  (css {:font-size "1.25em"
        :line-height "1.5em"
        :padding-bottom "0.4em"
        :margin-bottom "0.4em"
        :border-bottom "1px solid rgba(140, 130, 115, 0.12)"}))

(def $input
  (css {:background "#181a1b"
        :color "#9c9c9b"
        :border "1px solid rgba(140, 130, 115, 0.12)"
        :box-sizing "border-box"
        :padding "0.2em 0.5em"
        :margin "0.5em 0"}
       [:focus
        {:outline "1px solid rgba(140, 130, 115, 0.7)"}]))

(def $text-field
  (css {:width "100%"}
       ["&::placeholder"
        {:color "rgba(140, 130, 115, 0.5)"}]
       ["&:not(:focus:placeholder-shown):invalid"
        {:background "#431a1b"
         :border "1px solid #8b181a"}]))

(def $actions
  (css {:display :flex
        :flex-flow "row no-wrap"}))

(def $button
  (css {:width "100px"
        :margin-left "auto"}
       [:hover
        {:background "rgb(29, 32, 33)"}]))

(def $delete
  (css {:margin-left "1em"
        :background "#431a1b"
        :border "1px solid #8b181a"}
       [:hover
        {:background "#4d1e1f"}]))

(def $submit
  (css {:margin-left "1em"
        :background "rgb(68, 158, 0)"
        :border-color "rgb(85, 196, 0)"
        :color "white"}
       [:hover
        {:background "rgb(56, 135, 0)"
         :cursor "pointer"}]))

(defn dialog
  []
  (let [day @(subscribe [::subs/dialog-day])
        {:keys [title post-url calendar-url]} @(subscribe [::subs/day day])]
    [:dialog
     {:id "dialog"
      :class [$dialog]
      :on-click #(dispatch [::events/dialog-click %])
      :on-submit #(dispatch [::events/dialog-submit day %])}
     [:div
      {:class [$dialog-block]}
      [:header
       {:class [$dialog-head]}
       (str day "日の投稿を設定")]
      [:form
       {:method "dialog"
        :id "form"}
       [:input
        {:class [$input $text-field]
         :type :text
         :required true
         :name "title"
         :placeholder "タイトル"
         :default-value title}]
       [:input
        {:class [$input $text-field]
         :type :url
         :name "post"
         :placeholder "投稿URL"
         :default-value post-url}]
       [:input
        {:class [$input $text-field]
         :type :url
         :name "calendar"
         :placeholder "カレンダーURL"
         :default-value calendar-url}]
       [:div
        {:class [$actions]}
        [:button
         {:type :reset
          :class [$input $button]
          :on-click #(dispatch [::events/dialog-close])}
         "戻る"]
        [:button
         {:type :reset
          :class [$input $button $delete]
          :on-click #(dispatch [::events/clear-day day])}
         "削除"]
        [:button
         {:type :submit
          :class [$input $button $submit]}
         "参加"]]]]]))
