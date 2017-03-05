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
  ([db-spec] (room db-spec room-data))
  ([db-spec data] (jdbc/with-db-transaction [tx db-spec] (model/create! tx data))))

(deftest room-test
  (fact-group :integration :integration-isolated
    (facts "Room insertion"
      (with-state-changes [(before :facts (empty-and-create-tables))]
        (fact "Succeeds"
          (room db-spec) => (contains {:id id-pattern?}
                                      {:name "Some room name"}))))))
