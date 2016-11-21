(ns apiduck.events
    (:require [day8.re-frame.http-fx] 
              [re-frame.core :as re-frame]
              [ajax.core :as ajax]
              [day8.re-frame.undo :as undo :refer [undoable]]
              [apiduck.db :as db]
              [apiduck.utils :refer [change-block-type change-block drop-block add-block collapse-block]]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   db/default-db))

(re-frame/reg-event-db
 :change-click-msg
 (fn  [db [_ msg]]
   (assoc db :clicked-msg msg)))

(re-frame/reg-event-db
  :change-doc
  (undoable "changing doc attr value")
  (fn  [db [_ attr new-value]]
    (assoc db attr new-value)))


(re-frame/reg-event-db
 :change-attr-value
 (undoable "changing attr value")
 (fn  [db [_ schema-type block-id attr new-value]]
   (let [new-schema (change-block (get db schema-type) block-id attr new-value)]
    (assoc db schema-type new-schema))))

(re-frame/reg-event-db
 :change-variable-type
 (undoable "changing variable type")
 (fn  [db [_ schema-type block-id new-type]]
   (let [new-schema (change-block-type (get db schema-type) block-id new-type)]
    (assoc db schema-type new-schema))))

(re-frame/reg-event-db
 :drop-row
 (undoable "drop row")
 (fn  [db [_ schema-type block-id]]
   (let [new-schema (drop-block (get db schema-type) block-id)]
    (assoc db schema-type new-schema))))

(re-frame/reg-event-db
 :add-row
 (undoable "add row")
 (fn  [db [_ schema-type block-id]]
   (let [new-schema (add-block (get db schema-type) block-id)]
    (assoc db schema-type new-schema))))

(re-frame/reg-event-db
 :collapse-row
 (undoable "collapse row")
 (fn       [db [_ schema-type block-id value]]
   (let [new-schema (collapse-block (get db schema-type) block-id value)]
    (assoc db schema-type new-schema))))

(re-frame/reg-event-db
 :inject-dev-env
 (fn  [db [_ app-config]]
   (assoc db :app-config app-config)))


(re-frame/reg-event-fx                    ;; note the trailing -fx
  :handler-with-http                      ;; usage:  (dispatch [:handler-with-http])
  (fn [{:keys [db]} _]                    ;; the first param will be "world"
    {:db   (assoc db :loading true)   ;; causes the twirly-waiting-dialog to show??
     :http-xhrio {:method          :get
                  :uri             (str (get-in db [:app-config :host]) "/hello")
                  :timeout         8000                                           ;; optional see API docs
                  :response-format (ajax/json-response-format {:keywords? true})  ;; IMPORTANT!: You must provide this.
                  :on-success      [:good-http-result]
                  :on-failure      [:bad-http-result]}}))

(re-frame/reg-event-db
  :good-http-result
  (fn [db [_ content]]
    (println (str "good result!" content))
    db))


