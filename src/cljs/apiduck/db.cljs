(ns apiduck.db)

(def default-project
   {:project-name "Loading project name"
    :modules [{
      :module-name "Loading Module Name..."
      :endpoints [{
        :endpoint-name "Loading Enpoint Name..."
        :http-request-type "Loading..."
        :endpoint-description "Loading..."
        :request-schema {}
        :response-schema {}}]
      }]
    }
  )

(def app-config
  ;TODO change me to production ip
  {:host "http://spicyfox.com:3000"})


(def default-db
  {
    :app-config app-config
    :loading false
    :clicked-msg "no click yet1"
    :current-module-index 0
    :current-endpoint-index 0
    :in-edit-mode true
    :project-id "default_project_id"
    :project default-project
  })
