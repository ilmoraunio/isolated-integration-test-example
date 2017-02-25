(ns isolated-integration-test.db.room-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.room :as model]
            [schema.core :as s]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables]]))

(def room-data {:name "Pigeon room"})

(def room-expected (contains {:id string?}
                             {:name "Pigeon room"}))

(defn room
  ([] (let [data room-data] (room db-spec data)))
  ([tx data] (model/create! tx data)))

(deftest room-test
  (facts "Room insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact
        (room) => room-expected))))
