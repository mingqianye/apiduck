(ns apiduck.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route :refer [resources]]
            [ring.util.response :refer [response resource-response]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [apiduck.default-template :refer [template]]))

(defroutes routes
  (GET "/" [] (resource-response "index.html" {:root "public"}))
  (GET "/default_template" [] (fn [req] {:body template :status 200}) )
  (route/not-found (response {:message "Page not found!!!!"}))
  (resources "/"))

(def dev-handler (-> routes 
                     wrap-reload 
                     wrap-json-response))

(def handler (-> routes 
                 wrap-json-response))
