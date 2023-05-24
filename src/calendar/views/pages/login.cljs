(ns calendar.views.pages.login
  (:require [re-frame.core :as rf]
            [shadow.css :refer [css]]
            ["@tabler/icons" :refer [IconMail IconLock]]
            [calendar.events :as events]
            [calendar.events.routing :as routing]
            [calendar.routes.web :as-alias web]
            [calendar.subs :as subs]
            [calendar.views.atoms.button :as button]
            [calendar.views.atoms.input-field :as input-field]))

(def $column
  (css {:display "flex"
        :flex-flow "column nowrap"}))

(def $login
  (css {:height "100%"
        :justify-content "center"}))

(def $title
  (css {:text-align "center"
        :font-size "1.5em"
        :line-height "1.5em"
        :margin-bottom "15px"}))

(def $error
  (css {:margin-bottom "10px"}))

(def $actions
  (css {:display :flex
        :flex-flow "row no-wrap"
        :justify-content "flex-end"}))

(def $button
  (css {:border "1px solid rgba(140, 130, 115, 0.12)"
        :margin-left "auto"
        :padding "10px"
        :width "100px"}
       [:hover
        {:background "rgb(29, 32, 33)"}]))

(defn view
  []
  (when @(rf/subscribe [::subs/jwt])
    (rf/dispatch [::routing/navigate-to ::web/dashboard]))
  [:div
   {:class [$column $login]}
   [:header
    {:class [$title]}
    "ログイン"]
   (let [login-state @(rf/subscribe [::subs/login-state])]
     (when (= login-state :error)
       [:p
        {:class [$error]}
        "ログインできませんでした。"]))
   [:form
    {:class [$column]
     :method :post
     :on-submit (fn [event]
                  (.preventDefault event)
                  (rf/dispatch [::events/login-submit event])
                  true)}
    [input-field/view
     {:auto-complete "username"
      :max-length 200
      :min-length 5
      :name "email"
      :placeholder "developer@studist.tech"
      :prefix [:> IconMail {}]
      :required true
      :type "email"}]
    [input-field/view
     {:auto-complete "current-password"
      :max-length 1000
      :min-length 8
      :name "password"
      :placeholder "••••••"
      :prefix [:> IconLock {}]
      :required true
      :type "password"}]
    [:div
     {:class [$actions]}
     [button/generic
      {:label "ログイン"
       :type "submit"}]]]])
