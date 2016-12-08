(ns apiduck.events
    (:require [day8.re-frame.http-fx] 
              [re-frame.core :as re-frame]
              [ajax.core :as ajax]
              [day8.re-frame.undo :as undo :refer [undoable]]
              [apiduck.db :refer [default-db]]
              [apiduck.utils :refer [cook-project change-block-type change-block drop-block add-block collapse-block remove-nth new-endpoint new-module]]))

(re-frame/reg-event-db
 :initialize-db
 (fn  [_ _]
   default-db))

(re-frame/reg-event-db
 :change-click-msg
 (fn  [db [_ msg]]
   (assoc db :clicked-msg msg)))

(re-frame/reg-event-db
  :change-project
  (fn [db [_ attr value]]
    (assoc-in db [:project attr] value)))

(re-frame/reg-event-db
  :change-endpoint
  (undoable "changing doc attr value")
  (fn  [db [_ attr new-value]]
    (let [module-index (:current-module-index db)
          endpoint-index (:current-endpoint-index db)]
      (assoc-in db [:project :modules module-index :endpoints endpoint-index attr] new-value))))

(defn- change-request-response [& {:keys [db request-or-response update-function]}]
  (let [module-index (:current-module-index db)
        endpoint-index (:current-endpoint-index db)]
    (update-in db [:project 
                   :modules 
                   module-index 
                   :endpoints 
                   endpoint-index 
                   request-or-response] update-function)))

(re-frame/reg-event-db
 :change-row-attr-value
 (undoable "changing row attr value")
 (fn  [db [_ schema-type block-id attr new-value]]
   (change-request-response
     :db                  db
     :request-or-response schema-type
     :update-function     #(change-block % block-id attr new-value))))

(re-frame/reg-event-db
 :change-row-variable-type
 (undoable "changing row variable type")
 (fn  [db [_ schema-type block-id new-type]]
   (change-request-response
     :db                  db
     :request-or-response schema-type
     :update-function     #(change-block-type % block-id new-type))))

(re-frame/reg-event-db
 :drop-row
 (undoable "drop row")
 (fn  [db [_ schema-type block-id]]
   (change-request-response
     :db                  db
     :request-or-response schema-type
     :update-function     #(drop-block % block-id))))

(re-frame/reg-event-db
 :add-row
 (undoable "add row")
 (fn  [db [_ schema-type block-id]]
   (change-request-response
     :db                  db
     :request-or-response schema-type
     :update-function     #(add-block % block-id))))

(re-frame/reg-event-db
 :collapse-row
 (undoable "collapse row")
 (fn [db [_ schema-type block-id value]]
   (change-request-response
     :db                  db
     :request-or-response schema-type
     :update-function     #(collapse-block % block-id value))))

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


(re-frame/reg-event-fx                ;; note the trailing -fx
  :load-template                      ;; usage:  (dispatch [:handler-with-http])
  (fn [{:keys [db]} _]                ;; the first param will be "world"
    {:db   (assoc db :loading true)   ;; causes the twirly-waiting-dialog to show??
     :http-xhrio {:method          :get
                  :uri             (str (get-in db [:app-config :host]) 
                                        (str "/api/load_project/" (:project-id db)))
                  :timeout         8000                                           ;; optional see API docs
                  :response-format (ajax/json-response-format {:keywords? true})  ;; IMPORTANT!: You must provide this.
                  :on-success      [:initialize-template]
                  :on-failure      [:bad-http-result]}}))


(re-frame/reg-event-db
  :initialize-template
  (fn [db [_ endpoints]]
    (assoc db :project (cook-project endpoints))))

(re-frame/reg-event-db
  :change-current-endpoint-index
  (fn [db [_ module-index endpoint-index]]
    (assoc db :current-module-index module-index :current-endpoint-index endpoint-index)))

(re-frame/reg-event-db
  :drop-endpoint
  (undoable "drop endpoint")
  (fn [db [_ module-index endpoint-index]]
    (let [is-current? (= [(:current-module-index db) (:current-endpoint-index db)] [module-index endpoint-index])]
      (-> (if is-current?
            (assoc db :current-module-index 0 :current-endpoint-index 0)
            db)
          (update-in [:project :modules module-index :endpoints] #(remove-nth endpoint-index %))))))


(re-frame/reg-event-db
  :drop-module
  (undoable "drop module")
  (fn [db [_ module-index]]
    (let [is-current? (= (:current-module-index db) module-index)]
      (-> (if is-current?
            (assoc db :current-module-index 0 :current-endpoint-index 0)
            db)
          (update-in [:project :modules] #(remove-nth module-index %))))))

(re-frame/reg-event-db
  :add-endpoint
  (undoable "add endpoint")
  (fn [db [_ module-index]]
      (-> db
          (update-in [:project :modules module-index :endpoints] #(conj % (new-endpoint))))))

(re-frame/reg-event-db
  :add-module
  (undoable "add module")
  (fn [db [_]]
      (-> db
          (update-in [:project :modules] #(conj % (new-module))))))
