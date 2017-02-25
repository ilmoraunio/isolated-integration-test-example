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
  (facts "Message insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact "Succeeds"
        (jdbc/with-db-transaction [tx db-spec]
          (let [{user_id1 :username} (user tx {:username "foo" :password "hunter2"})
                {user_id2 :username} (user tx {:username "bar" :password "hunter2"})
                {room_id :id} (room)
                {participant_id1 :id}  (participant tx {:name "foo"
                                                        :username user_id1
                                                        :room_id room_id})
                {participant_id2 :id}  (participant tx {:name "bar"
                                                        :username user_id2
                                                        :room_id room_id})]
            (model/create! tx {:room_id room_id
                               :sender participant_id1
                               :recipient participant_id2
                               :message "hello world!"}) => (contains {:id string?}
                                                                      {:room_id string?}
                                                                      {:sender string?}
                                                                      {:recipient string?}
                                                                      {:message string?}))))
      (fact "Succeeds (isolated)"
        (jdbc/with-db-transaction [tx db-spec]
          (without-fk-constraints tx
            (model/create! tx {:room_id "ebe1b9be-f7a7-11e6-a440-573a04afc920"
                               :sender "f480dd34-f7a7-11e6-a440-0f01535615fc"
                               :recipient "f9086be2-f7a7-11e6-a440-13d4ffe62295"
                               :message "hello world!"}) => (contains {:id string?}
                                                                      {:room_id string?}
                                                                      {:sender string?}
                                                                      {:recipient string?}
                                                                      {:message string?})))))))
