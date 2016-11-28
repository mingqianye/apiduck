(ns apiduck.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [apiduck.utils :refer [current-endpoint]]))

(re-frame/reg-sub
 :whole-db
 (fn [db [_ _]]
   db))

(re-frame/reg-sub
 :endpoint-name
 (fn [db]
   (-> db
       current-endpoint
       :endpoint-name)))

(re-frame/reg-sub
 :http-request-type
 (fn [db]
   (-> db
       current-endpoint
       :http-request-type)))

(re-frame/reg-sub
 :endpoint-description
 (fn [db]
   (-> db
       current-endpoint
       :endpoint-description)))


(re-frame/reg-sub
 :request-schema
 (fn [db]
   (-> db
       current-endpoint
       :request-schema)))

(re-frame/reg-sub
 :response-schema
 (fn [db]
   (-> db
       current-endpoint
       :response-schema)))
