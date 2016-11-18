(ns apiduck.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :api-name
 (fn [db]
   (:api-name db)))

(re-frame/reg-sub
 :http-request-type
 (fn [db]
   (:http-request-type db)))

(re-frame/reg-sub
 :api-description
 (fn [db]
   (:api-description db)))

(re-frame/reg-sub
 :whole-db
 (fn [db]
   db))

(re-frame/reg-sub
 :request-schema
 (fn [db]
   (:request-schema db)))

(re-frame/reg-sub
 :response-schema
 (fn [db]
   (:response-schema db)))
