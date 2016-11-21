(ns apiduck.core
    (:require [reagent.core :as reagent]
              [re-frame.core :as re-frame]
              [apiduck.events]
              [apiduck.subs]
              [apiduck.views :as views]
              [apiduck.config :as config]))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (re-frame/dispatch-sync [:inject-dev-env {:host "http://localhost:3449"}])
    (println "dev mode")))

(defn mount-root []
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (re-frame/dispatch-sync [:initialize-db])
  (dev-setup)
  (re-frame/dispatch-sync [:initialize-template "default"])
  (mount-root))
