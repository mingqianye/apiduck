(ns apiduck.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route :refer [resources]]
            [ring.util.response :refer [response resource-response]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.reload :refer [wrap-reload]]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/hello" [] (fn [req] {:body {:a 1} :status 200}) )
  (route/not-found (response {:message "Page not found!!!!"}))
  (resources "/"))

(def dev-handler (-> routes 
                     wrap-reload 
                     wrap-json-response))

(def handler (-> routes 
                 wrap-json-response))

;; define the ring application
;(def app
;  (wrap-json-response handler))
;(def app
;  (-> routes
;      (middleware/wrap-json-body)
;      (middleware/wrap-json-params)
;      (middleware/wrap-json-response)))
