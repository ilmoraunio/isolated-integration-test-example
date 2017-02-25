(ns isolated-integration-test.db.message
  (:require [schema.core :as s]
            [yesql.core :refer [defquery]]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.db.model :as model]))


(s/defschema New {:room_id String
                  :sender String
                  :recipient String
                  :message String})
(s/defschema Model (into model/Model New))

(defquery sql-room-create<! "sql/message/create.sql" {:connection db-spec})

(s/defn create! [tx message :- New] {:post [(s/validate Model %)]}
  (sql-room-create<! message {:connection tx}))