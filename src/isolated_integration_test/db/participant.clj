(ns isolated-integration-test.db.participant
  (:require [schema.core :as s]
            [yesql.core :refer [defquery]]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.db.util :refer [initialize-query-data]]
            [isolated-integration-test.db.model :as model]))

(def common {:room_id s/Int
             :name String
             :users_id (s/maybe s/Int)})
(def New common)
(def Model (into model/Model common))

(defquery sql-participant-create<! "sql/participant/create.sql"
  {:connection db-spec})

(s/defn create! [tx participant :- New] {:post [(s/validate Model %)]}
  (sql-participant-create<! participant {:connection tx}))