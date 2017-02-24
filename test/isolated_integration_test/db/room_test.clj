(ns isolated-integration-test.db.room-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.room :as model]
            [schema.core :as s]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables]]))

(def room-data {:name "Pigeon room"})

(def room-expected (contains {:id integer?}
                             {:name "Pigeon room"}
                             {:created #(instance? java.util.Date %)}
                             {:updated #(instance? java.util.Date %)}
                             {:version 0}
                             {:deleted false}))

(defn room
  ([] (let [data room-data]
        (room data)))
  ([data] (model/create! db-spec data)))

(deftest room-test
  (facts "Room insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact
        (room) => room-expected))))
