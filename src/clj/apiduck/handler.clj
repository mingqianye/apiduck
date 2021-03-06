(ns apiduck.handler
  (:require [compojure.core :refer [GET defroutes]]
            [compojure.route :as route :refer [resources]]
            [ring.util.response :refer [response resource-response]]
            [ring.middleware.gzip :refer [wrap-gzip]]
            [ring.middleware.json :refer [wrap-json-response]]
            [ring.middleware.reload :refer [wrap-reload]]
            [apiduck.default-template :as template ]))

;TODO change me to a shorter uuid
(defn uuid [] (.toString (java.util.UUID/randomUUID)))

(defroutes routes
  (GET "/" [] (response {:message "Visit /new_project"}))
  (GET "/new_project" [] (ring.util.response/redirect (str "/project#" (uuid))))
  (GET "/project" [] (resource-response "project.html" {:root "public"}))
  (GET "/api/load_project/:project-id" [] (fn [req] {:body template/project :status 200}) )
  ;(route/not-found (resource-response "index.html" {:root "public"}))
  (resources "/")
  (route/not-found (response {:message "Page not found!!!!"}))
  )

(def dev-handler (-> routes 
                     wrap-reload 
                     wrap-json-response))

(def handler (-> routes 
                 wrap-json-response
                 wrap-gzip))
