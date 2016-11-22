(ns apiduck.db)

(def default-docs
  [{
   :api-name "Loading..."
   :http-request-type "Loading..."
   :api-description "Loading..."
   :request-type "Post"
   :request-schema {}
   :response-schema {}
  }])

(def app-config
  {:host "http://pppproduction:3449"})


(def default-db
  {
    :app-config app-config
    :loading false
    :clicked-msg "no click yet"
    :current-doc-index 0
    :docs default-docs
  })
