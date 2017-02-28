(ns isolated-integration-test.db.participant-test
  (:require [clojure.test :refer [deftest]]
            [midje.sweet :refer :all]
            [isolated-integration-test.db.model :refer [id-pattern?]]
            [isolated-integration-test.db.user-test :refer [user]]
            [isolated-integration-test.db.room-test :refer [room]]
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
  ([db-spec] (participant db-spec participant-data))
  ([db-spec data] (jdbc/with-db-transaction [tx db-spec] (model/create! tx (participant-data data)))))

(def room_id "ebe1b9be-f7a7-11e6-a440-573a04afc920")

(fact-group :integration
  (facts "Participant insertion"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact "Succeeds"
          (let [ _                   (user db-spec)
                {room_id :id}        (room db-spec)]
            (participant db-spec {:room_id room_id}) => (contains {:id id-pattern?}
                                                     {:room_id room_id}
                                                     {:name "Foobar-participant"}
                                                     {:username "foobar"}))))))

(fact-group :integration-isolated
  (facts "Participant insertion (isolated)"
    (with-state-changes [(before :facts (empty-and-create-tables))]
      (fact "Succeeds"
        (jdbc/with-db-transaction [tx db-spec]
          (without-fk-constraints tx
            (participant tx {:room_id room_id}) => (contains {:id id-pattern?}
                                                             {:room_id room_id}
                                                             {:name "Foobar-participant"}
                                                             {:username "foobar"})))))))