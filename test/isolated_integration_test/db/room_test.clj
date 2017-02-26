(ns isolated-integration-test.db.room-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.model :refer [id-pattern?]]
            [isolated-integration-test.db.room :as model]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables]]
            [clojure.java.jdbc :as jdbc]))

(def room-data {:name "Some room name"})

(defn room
  ([tx] (room tx room-data))
  ([tx data] (model/create! tx data)))

(deftest room-db
  (facts "Room insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact "Succeeds"
        (jdbc/with-db-transaction [tx db-spec]
          (room tx) => (contains {:id id-pattern?}
                                 {:name "Some room name"}))))))