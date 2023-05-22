(ns calendar.db.calendars
  (:require [calendar.db.core :refer [defquery]]))

(defquery active
  []
  {:select [:title :description :created-by :first-day :last-day]
   :from [:calendars]
   :where [:and
           [:<= :public-day :current-date]
           [:or
            [:= :close-day nil]
            [:>= :close-day :current-date]]]
   :order-by [[:id :desc]]
   :limit 1})
