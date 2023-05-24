(ns calendar.views.atoms.date-field
  (:require [shadow.css :refer [css]]))

(def $wrap
  (css {:align-content "center"
        :align-items "baseline"
        :display "flex"
        :flex-flow "row-reverse nowrap"
        :margin-bottom "10px"
        :min-width "220px"}))

(def $label
  (css {:flex-grow "1"}
       ["input:disabled + &"
        {:color "rgba(140, 130, 115, 0.5)"}]))

(def $field
  (css {:background "#181a1b"
        :border "1px solid rgba(140, 130, 115, 0.12)"
        :margin-left "10px"
        :padding "10px 10px 10px 15px"
        :width "170px"}
       ["&:disabled"
        {:color "rgba(140, 130, 115, 0.5)"}]
       ["&:focus"
        {:outline "1px solid rgba(140, 130, 115, 0.7)"}]))

(defn view
  [{:keys [label] :as opts}]
  (let [field-opts (into {:class [$field]
                          :type "date"}
                         (dissoc opts :class :label :type))]
    [:label
     {:class [$wrap]}
     [:input
      field-opts]
     [:span
      {:class [$label]}
      (str label)]]))
