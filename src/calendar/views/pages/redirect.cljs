(ns calendar.views.pages.redirect
  (:require [re-frame.core :as rf]
            [calendar.events :as events]
            [calendar.routes.web :as-alias web]
            [calendar.subs :as subs]))

(defn view
  "どっちかにリダイレクトするだけ"
  [_]
  (if @(rf/subscribe [::subs/jwt])
    (rf/dispatch [::events/navigate-to ::web/dashboard])
    (rf/dispatch [::events/navigate-to ::web/calendar]))
  ;; とりあえずなにかダミーのビューを返す必要はある
  [:div])
