(ns calendar.views.pages.redirect
  (:require [re-frame.core :as rf]
            [calendar.events.routing :as routing]
            [calendar.routes.web :as-alias web]
            [calendar.subs :as subs]))

(defn view
  "どっちかにリダイレクトするだけ"
  [_]
  (if @(rf/subscribe [::subs/jwt])
    (rf/dispatch [::routing/navigate-to ::web/dashboard])
    (rf/dispatch [::routing/navigate-to ::web/calendar]))
  ;; とりあえずなにかダミーのビューを返す必要はある
  [:div])
