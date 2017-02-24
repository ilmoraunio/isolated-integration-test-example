(ns   isolated-integration-test.db.room
  (:require [schema.core :as s]
            [yesql.core :refer [defquery]]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.db.util :refer [initialize-query-data]]
            [isolated-integration-test.db.model :as model]))

(s/defschema New {:name String})
(s/defschema Model (into model/Model
                         {:name String}))

(defquery sql-room-create<! "sql/room/create.sql"
  {:connection db-spec})

(s/defn create! [tx room :- New] {:post [(s/validate Model %)]}
  (sql-room-create<! room {:connection tx}))