(ns calendar.views.atoms.message
  (:require [shadow.css :refer [css]]))

(def $message
  (css {:margin "auto"
        :min-width "min-content"
        :text-align "center"}))

(defn view
  [& msg] ;; 複数の要素も渡せるので & で一括受取
  [:p
   {:class [$message]}
   msg])
