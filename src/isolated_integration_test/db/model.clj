(ns isolated-integration-test.db.model
  (require [schema.core :as s]))

(def Model
  {:id s/Int
   :created java.util.Date
   :updated java.util.Date
   :version s/Int
   :deleted Boolean})