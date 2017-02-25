(ns isolated-integration-test.db.user
  (:require [schema.core :as s]
            [yesql.core :refer [defquery]]
            [isolated-integration-test.conf.config :refer [db-spec]]))

(s/defschema New {:username String
                  :password String})
(s/defschema Model New)

(defquery sql-user-create<! "sql/user/create.sql" {:connection db-spec})

(s/defn create! [tx user :- New] {:post [(s/validate Model %)]}
  (sql-user-create<! user {:connection tx}))