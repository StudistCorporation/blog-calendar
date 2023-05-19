(ns calendar.db.calendars
  (:require [calendar.db.core :refer [defquery]]))

(defquery active
  []
  {:select [:title :description :created-by :first-day :last-day]
   :from [:calendars]
   :where [:and
           [:<= :public-day :current_date]
           [:or
            [:= :close-day nil]
            [:>= :close-day :current_date]]]
   :order-by [[:id :desc]]
   :limit 1})
