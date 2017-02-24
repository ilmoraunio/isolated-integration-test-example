(ns isolated-integration-test.db.user-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.user :as model]
            [schema.core :as s]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables]])
  (import java.sql.BatchUpdateException))


(def user-data {:username "foobar"
               :full_name "Foo Bar"
               :password "hunter2"})

(def user-data-expected (contains {:id integer?} 
                                 {:username "foobar"}
                                 {:full_name "Foo Bar"}
                                 {:password "hunter2"}
                                 {:created #(instance? java.util.Date %)}
                                 {:updated #(instance? java.util.Date %)}
                                 {:version 0}
                                 {:deleted false}))

(defn user
  ([] (let [data user-data]
        (model/create! db-spec data)))

  ([{username :username :as input}]
      (let [data (assoc user-data :username username)]
        (model/create! db-spec data))))

(deftest user-test
  (facts "User insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact
        (user) => user-data-expected))))