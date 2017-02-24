(ns isolated-integration-test.test-util
  (:require [isolated-integration-test.conf.config :refer [db-spec
                                              get-table-names
                                              get-table-names-without-meta]]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.conf.migrations :as migrations]
            [cheshire.core :as cheshire]
            [buddy.sign.jws :as jws]
            [clj-time.core :as t]
            [environ.core :refer [env]]
            [clojure.java.jdbc :as jdbc]
            [ring.mock.request :as mock]))

(defn- empty-all-tables [conn]
  (if-let [table-name-count (count (get-table-names-without-meta))]
    (if (> table-name-count 0)
      (jdbc/execute! conn [(str "TRUNCATE TABLE "
                             (clojure.string/join ", " (map :table_name (get-table-names-without-meta)))
                             " CASCADE")]))))

;; public

(defn empty-and-create-tables []
  (empty-all-tables db-spec)
  (if (= 0 (count (get-table-names)))
    (migrations/migrate)))

(defmacro without-fk-constraints [tx & body]
  `(do
    (jdbc/execute! ~tx ["SET session_replication_role = replica"])
    (let [result# (do ~@body)]
      (jdbc/execute! ~tx ["SET session_replication_role = DEFAULT"])
      result#)))

