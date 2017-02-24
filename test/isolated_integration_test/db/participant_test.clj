(ns isolated-integration-test.db.participant-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.participant :as model]
            [schema.core :as s]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables
                                                         without-fk-constraints]]
            [clojure.java.jdbc :as jdbc]))

(defn participant-data
  ([{:keys [room_id name users_id]
     :or {name "Room group" users_id 1}}]
   {:room_id room_id
    :name name
    :users_id users_id}))

(def participant-expected (contains {:id integer?}
                                  {:room_id integer?}
                                  {:name "Room group"}
                                  {:users_id 1}
                                  {:created #(instance? java.util.Date %)}
                                  {:updated #(instance? java.util.Date %)}
                                  {:version 0}
                                  {:deleted false}))

(defn participant
  ([input] (participant db-spec input))
  ([tx input] (model/create! tx (participant-data input))))

(deftest participant-test
  (facts "Participant insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact
        (jdbc/with-db-transaction [tx db-spec]
          (without-fk-constraints tx
            (participant tx {:room_id 1}) => participant-expected))))))