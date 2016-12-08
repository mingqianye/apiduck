(ns apiduck.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [apiduck.utils :refer [current-endpoint]]))

(re-frame/reg-sub
 :whole-db
 (fn [db [_ _]]
   db))

(re-frame/reg-sub
  :db
  (fn [db [_ attr-name]]
    (attr-name db)))

(re-frame/reg-sub
 :current-endpoint
 (fn [db [_ attr-name]]
   (-> db
       current-endpoint
       attr-name)))

(re-frame/reg-sub
 :left-nav-bar-items
 (fn [db]
  (let [modules (get-in db [:project :modules])
        enumerate (fn [coll] (map-indexed vector coll))]
    (flatten (for [[index-m m] (enumerate modules)]
      (cons {:level :major :label (:module-name m) :module-index index-m :is-endpoint false}
            (for [[index-e e] (enumerate (:endpoints m))]
              {:module-index index-m :endpoint-index index-e :level :minor :label (:endpoint-name e) :is-endpoint true})))))))


