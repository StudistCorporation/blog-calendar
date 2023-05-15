(ns calendar.views.organisms.calendar
  (:require [shadow.css :refer [css]]
            [calendar.views.molecules.day-cell :refer [day-cell]]))

(def $table
  (css {:margin "auto"
        :max-width "70%"
        :border "1px solid rgba(140, 130, 115, 0.12)"}))

(def $days-of-week
  (css {:border "1px solid rgba(140, 130, 115, 0.12)"
        :color "rgba(156, 156, 156, 0.8)"
        :padding "0.5em 0"}))

(defn calendar
  []
  (let [before (-> "2022/12/01" (js/Date.) (.getDay) (dec))
        after (-> "2022/12/31" (js/Date.) (.getDay) (dec))]
    [:table
     {:class [$table]}
     [:thead
      [:tr
       (map
        (fn [day]
          [:th
           {:class [$days-of-week]
            :key (gensym)}
           day])
        ["月" "火" "水" "木" "金" "土" "日"])]]
     [:tbody
      ;; first week
      [:tr
       {:key (gensym)}
       ;; the days from november in the first week
       (map
        (fn [n]
          [day-cell false n])
        (range (- 31 before) 31))
       ;; first days of december in the first week
       (map
        (fn [day]
          [day-cell true day])
        (range 1 (- 8 before)))]
      ;; "normal" weeks
      (->> (range (- 8 before) (- 31 after))
           (map
            (fn [n]
              [day-cell (< n 26) n]))
           (partition 7)
           (map
            (fn [week]
              [:tr
               {:key (gensym)}
               week])))
      ;; last week (if any)
      (when-let [last-days (not-empty (range (- 31 after) 32))]
        [:tr
         {:key (gensym)}
         (map
          (fn [n]
            [day-cell (< n 26) n])
          last-days)
         (map
          (fn [n]
            [day-cell false n])
          (range 1 (- 7 after)))])]]))
