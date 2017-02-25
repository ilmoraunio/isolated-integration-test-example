(ns isolated-integration-test.conf.config
  (:require [yesql.core :refer [defquery]]
            [environ.core :refer [env]]))

(def db-spec 
  {:connection-uri (env :connection-uri)})

(defquery get-table-names-without-meta "sql/information_schema/get-table-names-without-meta.sql"
  {:connection db-spec})

(defquery get-table-names "sql/information_schema/get-table-names.sql"
  {:connection db-spec})