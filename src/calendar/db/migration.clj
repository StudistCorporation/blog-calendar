(ns calendar.db.migration
  (:require [ragtime.next-jdbc :as jdbc]
            [ragtime.repl :as repl]
            [calendar.db.core :refer [datasource]]))

(defn config
  []
  {:datastore  (jdbc/sql-database datasource)
   :migrations (jdbc/load-resources "migrations")})

(defn migrate
  []
  (repl/migrate (config)))

(defn rollback
  []
  (repl/rollback (config)))
