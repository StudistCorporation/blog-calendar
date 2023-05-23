(ns calendar.views.atoms.auth-field
  (:require [shadow.css :refer [css]]))

(def $field
  (css {:display "flex"
        :flex-flow "row-reverse nowrap"
        :flex-grow "1"
        :margin-bottom "10px"}
       [:focus-within
        {:outline "1px solid rgba(140, 130, 115, 0.7)"}]))

(def $prefix
  (css {:background "#181a1b"
        :border "1px solid rgba(140, 130, 115, 0.12)"
        :border-right "none"
        :min-width "15px"
        :width "min-content"
        :padding "10px"}
       [":not(:focus):not(:placeholder-shown):invalid + &"
        {:background "#431a1b"
         :border "1px solid #8b181a"
         :border-right "none"}]))

(def $input
  (css {:background "inherit"
        :border "1px solid rgba(140, 130, 115, 0.12)"
        :border-left "none"
        :padding "10px 10px 10px 0"
        :flex-grow "1"}
       [:focus
        {:outline "none"}]
       ["&::placeholder"
        {:color "rgba(140, 130, 115, 0.5)"}]
       ["&:not(:focus):not(:placeholder-shown):invalid"
        {:background "#431a1b"
         :border "1px solid #8b181a"
         :border-left "none"}]))

(defn view
  [{:keys [prefix type] :as opts}
   {:or {prefix "" type "text"}}]
  (let [field-opts (into {:class [$input]
                          :type type}
                         (dissoc opts :class :prefix :type))]
    [:label
     {:class [$field]}
     [:input
      field-opts]
     [:div
      {:class [$prefix]}
      prefix]]))
