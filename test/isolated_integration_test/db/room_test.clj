(ns isolated-integration-test.db.room-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.room :as model]
            [schema.core :as s]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables]]
            [clojure.java.jdbc :as jdbc]))

(def room-data {:name "Some room name"})
(def room-expected (contains {:id string?}
                             {:name "Some room name"}))

(defn room
  ([] (let [data room-data] (room db-spec data)))
  ([tx data] (jdbc/with-db-transaction [tx tx] (model/create! tx data))))

(deftest room-db
  (facts "Room insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact "Succeeds"
        (jdbc/with-db-transaction [tx db-spec]
          (room) => room-expected)))))