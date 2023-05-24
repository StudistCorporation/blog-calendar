(ns calendar.views.organisms.dialog
  (:require [shadow.css :refer [css]]
            [re-frame.core :as rf]
            [calendar.events.dialog :as dialog]
            [calendar.subs :as subs]
            [calendar.views.atoms.button :as button]
            [calendar.views.atoms.input-field :as input-field]))

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

(def $actions
  (css {:display :flex
        :flex-flow "row no-wrap"
        :justify-content "flex-end"}))

(defn view
  []
  (let [day @(rf/subscribe [::subs/dialog-day])
        {:keys [title post-url calendar-url]} @(rf/subscribe [::subs/day day])]
    [:dialog
     {:id "dialog"
      :class [$dialog]
      :on-click #(rf/dispatch [::dialog/click %])
      :on-submit #(rf/dispatch [::dialog/submit day %])}
     [:div
      {:class [$dialog-block]}
      [:header
       {:class [$dialog-head]}
       (str day "日の投稿を設定")]
      [:form
       {:method "dialog"
        :id "form"}
       [input-field/view
        {:required true
         :name "title"
         :placeholder "タイトル"
         :default-value title}]
       [input-field/view
        {:type :url
         :name "post"
         :placeholder "投稿URL"
         :default-value post-url}]
       [input-field/view
        {:type :url
         :name "calendar"
         :placeholder "カレンダーURL"
         :default-value calendar-url}]
       [:div
        {:class [$actions]}
        [button/generic
         {:label "戻る"
          :on-click #(rf/dispatch [::dialog/close])
          :type :reset}]
        [button/delete
         {:label "削除"
          :on-click #(rf/dispatch [:calendar.events/clear-day day])}]
        [button/create
         {:label "参加"}]]]]]))
