(ns calendar.views.atoms.auth-field
  (:require [shadow.css :refer [css]]))

(def $field
  (css {:background "#181a1b"
        :border "1px solid rgba(140, 130, 115, 0.12)"
        :display "flex"
        :flex-flow "row nowrap"
        :margin-bottom "10px"}
       [:focus-within
        {:outline "1px solid rgba(140, 130, 115, 0.7)"}]))

(def $prefix
  (css {:min-width "15px"
        :padding "10px"}))

(def $input
  (css {:background "inherit"
        :padding "10px 10px 10px 0"
        :width "220px"}
       [:focus
        {:outline "none"}]
       ["&::placeholder"
        {:color "rgba(140, 130, 115, 0.5)"}]))

(defn view
  [{:keys [prefix type] :as opts}
   {:or {prefix "" type "text"}}]
  (let [field-opts (into {:class [$input]
                          :type type}
                         (dissoc opts :class :prefix :type))]
    [:div
     {:class [$field]}
     [:div
      {:class [$prefix]}
      prefix]
     [:input
      field-opts]]))
