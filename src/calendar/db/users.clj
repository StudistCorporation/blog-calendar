(ns calendar.db.users
  (:require [calendar.db.core :refer [defquery]]))

(defquery by-auth-id
  [uid]
  {:select [:*]
   :from [:users]
   :where [:and
           [:<> :status [[:user_status "disabled"]]]
           [:= :auth-id [[:uuid uid]]]]
   :limit 1})

(defquery many-by-id
  [ids]
  {:select [:id :display-name :email-md5]
   :from [:users]
   :where [:in :id (vec ids)]})
