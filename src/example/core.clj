(ns example.core
  (:gen-class)
  (:use korma.db)
  (:use korma.core)
  (:require [clojure.string :as str]))

(def db-spec (h2 {:db "resources/db/korma.db"
             :user "sa"
             :password ""
             :naming {:keys str/lower-case
                      ;; set map keys to lower
                      :fields str/upper-case}}))

(def db (create-db db-spec))
(declare messages)
(defentity users
  (pk :id)
  (table :USERS)
  (database db)
  (has-many messages)
  (entity-fields :id :name :last_seen))
(defentity messages
  (pk :id)
  (table :MESSAGES)
  (database db)
  (entity-fields :id :message :timestamp)
  (belongs-to users))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println (sql-only (select messages (with users)))))
