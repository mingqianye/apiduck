(ns apiduck.subs
    (:require-macros [reagent.ratom :refer [reaction]])
    (:require [re-frame.core :as re-frame]
              [apiduck.utils :refer [current-endpoint]]))

(re-frame/reg-sub
 :whole-db
 (fn [db [_ _]]
   db))

(re-frame/reg-sub
 :current-endpoint
 (fn [db [_ attr-name]]
   (-> db
       current-endpoint
       attr-name)))
