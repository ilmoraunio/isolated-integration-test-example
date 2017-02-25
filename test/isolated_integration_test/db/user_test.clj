(ns isolated-integration-test.db.user-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.user :as model]
            [schema.core :as s]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables]])
  (import java.sql.BatchUpdateException))


(def user-data {:username "foobar"
                :password "hunter2"})

(def user-data-expected (contains {:username "foobar"}
                                  {:password "hunter2"}))

(defn user
  ([] (let [data user-data] (user db-spec data)))
  ([tx data] (model/create! tx data)))

(deftest user-test
  (facts "User insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact
        (user) => user-data-expected))))