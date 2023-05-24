(ns calendar.views.atoms.button
  (:require [shadow.css :refer [css]]))

(def $button
  (css {:box-sizing "border-box"
        :padding "0.2em 0.5em"
        :margin-left "1em"
        :width "100px"}
       [:focus
        {:outline "1px solid rgba(140, 130, 115, 0.7)"}]))

(def $generic
  (css {:background "#181a1b"
        :border "1px solid rgba(140, 130, 115, 0.12)"
        :color "#9c9c9b"}
       [:hover
        {:background "rgb(29, 32, 33)"}]))

(def $delete
  (css {:background "#431a1b"
        :border "1px solid #8b181a"}
       [:hover
        {:background "#4d1e1f"}]))

(def $create
  (css {:background "rgb(68, 158, 0)"
        :border-color "rgb(85, 196, 0)"
        :color "white"}
       [:hover
        {:background "rgb(56, 135, 0)"}]))

(defn generic
  [{:keys [class label] :as opts}]
  [:button
   (into {:class (into [$button] (or (not-empty class) [$generic]))}
         (dissoc opts :class :label))
   (str label)])

(defn delete
  [{:keys [class] :as opts}]
  [generic
   (into {:type :reset
          :class (into [$button $delete] class)}
         (dissoc opts :class))])

(defn create
  [{:keys [class] :as opts}]
  [generic
   (into {:type :submit
          :class (into [$button $create] class)}
         (dissoc opts :class))])
