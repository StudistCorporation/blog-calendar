(ns calendar.views.atoms.git-link
  (:require [shadow.css :refer [css]]
            ["@tabler/icons" :refer [IconBrandGithub]]))

(def $link-wrap
  (css {:position "fixed"
        :right "0.5em"
        :bottom "0.5em"}))

(def $link-a
  (css {:display "block"
        :padding "1em"}))

(def $link-icon
  (css {}))

(defn view
  []
  [:div
   {:class [$link-wrap]}
   [:a
    {:class [$link-a]
     :title "Source code available on Github"
     :href "https://github.com/StudistCorporation/blog-calendar"}
    [:> IconBrandGithub
     {:size 32
      :stroke 2
      :class [$link-icon]}]]])
