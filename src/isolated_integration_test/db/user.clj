(ns isolated-integration-test.db.user
  (:require [schema.core :as s]
            [yesql.core :refer [defquery]]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.db.model :as model]
            [isolated-integration-test.db.util :refer [initialize-query-data]]))

(s/defschema New {:username String
                  :full_name String
                  :password String})
(s/defschema Model (into model/Model New))

(defquery sql-user-create<! "sql/user/create.sql"
  {:connection db-spec})

(s/defn create! [tx user :- New] {:post [(s/validate Model %)]}
  (sql-user-create<! user {:connection tx}))