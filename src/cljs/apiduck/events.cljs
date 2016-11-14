(ns apiduck.events
    (:require [re-frame.core :as re-frame]
              [day8.re-frame.undo :as undo :refer [undoable]]
              [apiduck.db :as db]
              [apiduck.utils :refer [change-block]]))

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
   (let [new-schema (change-block (:current-schema db) block-id attr new-value)]
    (assoc db :current-schema new-schema))))
