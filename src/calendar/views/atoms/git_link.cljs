(ns calendar.views.atoms.git-link
  (:require [shadow.css :refer [css]]
            ["@tabler/icons" :refer [IconBrandGithub]]))

(def $link-wrap
  (css {:position "absolute"
        :right "0.5em"
        :bottom "0.5em"}))

(def $link-a
  (css {:display "block"
        :padding "1em"}))

(def $link-icon
  (css {}))

(defn git-link
  []
  [:div
   {:class [$link-wrap]}
   [:a
    {:class [$link-a]
     :title "Source code available on Github"
     :href "https://github.com/valerauko/community-calendar"}
    [IconBrandGithub
     {:size 32
      :stroke 2
      :class [$link-icon]}]]])
