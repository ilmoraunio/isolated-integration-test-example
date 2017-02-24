(ns isolated-integration-test.conf.config
  (:require [yesql.core :refer [defquery]]
            [environ.core :refer [env]]
            [isolated-integration-test.conf.migrations :as migrations]
            [clojure.java.jdbc :as jdbc]))

(def db-spec 
  {:connection-uri (env :connection-uri)})

(defquery get-table-names-without-meta "sql/information_schema/get-table-names-without-meta.sql"
  {:connection db-spec})

(defquery get-table-names "sql/information_schema/get-table-names.sql"
  {:connection db-spec})