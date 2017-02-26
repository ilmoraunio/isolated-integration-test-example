(ns isolated-integration-test.db.user-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.user :as model]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables]]
            [clojure.java.jdbc :as jdbc]))


(def user-data {:username "foobar"
                :password "hunter2"})

(defn user
  ([] (let [data user-data] (user db-spec data)))
  ([tx data] (jdbc/with-db-transaction [tx tx] (model/create! tx data))))

(deftest user-db
  (facts "User insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact
        (user) => (contains {:username "foobar"}
                            {:password "hunter2"})))))