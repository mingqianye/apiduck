(ns apiduck.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :name
 (fn [db]
   (:name db)))

(re-frame/reg-sub
 :whole-db
 (fn [db]
   db))

(re-frame/reg-sub
 :current-schema
 (fn [db]
   (:current-schema db)))
