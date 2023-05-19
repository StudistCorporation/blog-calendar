(ns calendar.db.calendars
  (:require [calendar.db.core :refer [defquery]]))

(defquery active
  []
  {:select [:*]
   :from [:calendars]
   :where [:and
           [:<= :public-day :current_date]
           [:or
            [:= :close-day nil]
            [:>= :close-day :current_date]]]
   :order-by [[:id :desc]]
   :limit 1})
