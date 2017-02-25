(ns isolated-integration-test.db.participant-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.participant :as model]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables
                                                         without-fk-constraints]]
            [clojure.java.jdbc :as jdbc]))

(defn participant-data
  ([{:keys [room_id name username]
     :or {name "Foobar-participant" username "foobar"}}]
   {:room_id room_id
    :name name
    :username username}))

(defn participant
  ([] (participant db-spec participant-data))
  ([tx input] (model/create! tx (participant-data input))))

(deftest participant-db
  (facts "Participant insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact "Succeeds"
        (jdbc/with-db-transaction [tx db-spec]
          (without-fk-constraints tx
            (participant tx {:room_id "foobar"}) => (contains {:id string?}
                                                              {:room_id string?}
                                                              {:name "Foobar-participant"}
                                                              {:username "foobar"})))))))