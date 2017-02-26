(ns isolated-integration-test.db.model
  (:require [schema.core :as s]))

(def id-pattern? #".{8}-.{4}-.{4}-.{4}-.{12}")
(def Model {:id (s/pred #(re-matches id-pattern? %))})