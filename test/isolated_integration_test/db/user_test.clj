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
  ([db-spec] (user db-spec user-data))
  ([db-spec data] (jdbc/with-db-transaction [tx db-spec] (model/create! tx data))))

(deftest user-test
  (fact-group :integration :integration-isolated
    (facts "User insertion"
      (with-state-changes [(before :facts (empty-and-create-tables))]
        (fact "Succeeds"
          (user db-spec) => (contains {:username "foobar"}
                                      {:password "hunter2"}))))))
