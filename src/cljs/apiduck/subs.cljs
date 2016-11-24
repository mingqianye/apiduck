(ns apiduck.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(defn- current-doc [db]
  (get (:docs db) (:current-doc-index db)))

(re-frame/reg-sub
 :endpoint-name
 (fn [db]
   (-> db
       current-doc
       :endpoint-name)))

(re-frame/reg-sub
 :http-request-type
 (fn [db]
   (-> db
       current-doc
       :http-request-type)))

(re-frame/reg-sub
 :endpoint-description
 (fn [db]
   (-> db
       current-doc
       :endpoint-description)))

(re-frame/reg-sub
 :whole-db
 (fn [db]
   db))

(re-frame/reg-sub
 :request-schema
 (fn [db]
   (-> db
       current-doc
       :request-schema)))

(re-frame/reg-sub
 :response-schema
 (fn [db]
   (-> db
       current-doc
       :response-schema)))
