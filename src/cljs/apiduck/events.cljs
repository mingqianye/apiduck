(ns apiduck.events
    (:require [re-frame.core :as re-frame]
              [day8.re-frame.undo :as undo :refer [undoable]]
              [apiduck.db :as db]
              [apiduck.utils :refer [change-block drop-block add-block collapse-block]]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :change-click-msg
 (fn  [db [_ msg]]
   (assoc db :clicked-msg msg)))

(re-frame/reg-event-db
 :change-attr-value
 (undoable "changing attr value")
 (fn  [db [_ block-id attr new-value]]
   (let [new-schema (change-block (:request-schema db) block-id attr new-value)]
    (assoc db :request-schema new-schema))))


(re-frame/reg-event-db
 :drop-row
 (undoable "drop row")
 (fn  [db [_ block-id]]
   (let [new-schema (drop-block (:request-schema db) block-id)]
    (assoc db :request-schema new-schema))))

(re-frame/reg-event-db
 :add-row
 (undoable "add row")
 (fn  [db [_ block-id]]
   (let [new-schema (add-block (:request-schema db) block-id)]
    (assoc db :request-schema new-schema))))

(re-frame/reg-event-db
 :collapse-row
 (undoable "collapse row")
 (fn  [db [_ block-id value]]
   (let [new-schema (collapse-block (:request-schema db) block-id value)]
    (assoc db :request-schema new-schema))))
