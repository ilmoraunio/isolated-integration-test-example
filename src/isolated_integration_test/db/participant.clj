(ns isolated-integration-test.db.participant
  (:require [schema.core :as s]
            [yesql.core :refer [defquery]]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.db.model :as model]))

(def common {:room_id String
             :name String
             :username (s/maybe String)})
(def New common)
(def Model (into model/Model common))

(defquery sql-participant-create<! "sql/participant/create.sql" {:connection db-spec})

(s/defn create! [tx participant :- New] {:post [(s/validate Model %)]}
  (sql-participant-create<! participant {:connection tx}))