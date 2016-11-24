(ns apiduck.db)

(def default-endpoints
  [{
   :endpoint-name "Loading..."
   :http-request-type "Loading..."
   :endpoint-description "Loading..."
   :request-type "Post"
   :request-schema {}
   :response-schema {}
  }])

(def app-config
  ;TODO change me to production ip
  {:host "http://localhost:3449"})


(def default-db
  {
    :app-config app-config
    :loading false
    :clicked-msg "no click yet"
    :current-doc-index 0
    :project-id ""
    :endpoints default-endpoints
  })
