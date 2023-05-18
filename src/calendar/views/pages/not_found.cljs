(ns calendar.views.pages.not-found
  (:require [reitit.frontend.easy :refer [href]]
            [calendar.routes.web :as-alias routes]))

(defn view
  [_]
  [:p
   "該当するページがありません。"
   [:a
    {:href (href ::routes/current)}
    "ホームに戻る"]])
