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
  ([tx] (user tx user-data))
  ([tx data] (model/create! tx data)))

(deftest user-db
  (facts "User insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact
        (jdbc/with-db-transaction [tx db-spec]
          (user tx) => (contains {:username "foobar"}
                                 {:password "hunter2"}))))))