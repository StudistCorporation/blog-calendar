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

(defn view
  [{:keys [comment end filled start]}]
  (let [start-date (js/Date. start)
        end-date (js/Date. end)
        year (.getFullYear start-date)
        month (.getMonth start-date)
        first-cell (- 2 (.getDay (js/Date. year month 1)))
        last-day (.getDate (js/Date. year month 0))
        last-cell (+ last-day (rem last-day 7))]
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
      (->> (range first-cell last-cell)
           (map
            (fn [n]
              [day-cell
               (<= (.getDate start-date) n (.getDate end-date))
               (js/Date. year month n)]))
           (partition 7)
           (map
            (fn [week]
              [:tr
               {:key (gensym)}
               week])))]]))
