(ns isolated-integration-test.db.message-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.user-test :refer [user]]
            [isolated-integration-test.db.room-test :refer [room]]
            [isolated-integration-test.db.participant-test :refer [participant]]
            [isolated-integration-test.db.message :as model]
            [schema.core :as s]
            [isolated-integration-test.conf.config :refer [db-spec]]
            [isolated-integration-test.test-util :refer [empty-and-create-tables
                                                         without-fk-constraints]]
            [clojure.java.jdbc :as jdbc]))

(defn message
  ([input] (participant db-spec input)))

(deftest message-db
  (facts "Insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact "Succeeds"
        (let [{user_id1 :username} (user db-spec {:username "foo" :password "hunter2"})
              {user_id2 :username} (user db-spec {:username "bar" :password "hunter2"})
              {room_id :id} (room)
              {participant_id1 :id}  (participant db-spec {:name "foo"
                                                           :username user_id1
                                                           :room_id room_id})
              {participant_id2 :id}  (participant db-spec {:name "bar"
                                                           :username user_id2
                                                           :room_id room_id})]
          (model/create! db-spec {:room_id room_id
                                  :sender participant_id1
                                  :recipient participant_id2
                                  :message "hello world!"}) => (contains {:id string?}
                                                                         {:room_id string?}
                                                                         {:sender string?}
                                                                         {:recipient string?}
                                                                         {:message string?}))))))