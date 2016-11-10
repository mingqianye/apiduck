(ns apiduck.views
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]
              [apiduck.row-button :as row-button]))

(defn title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [re-com/title
       :label (str "Hello from " @name)
       :level :level1])))

(defn db-printout []
  (let [whole-db (re-frame/subscribe [:whole-db])]
    (fn []
      [:div 
        [:hr]
        [:h3 "Database Atom"]
        [:pre (with-out-str (cljs.pprint/pprint @whole-db))]])))

(defn main-panel []
  (fn []
    [re-com/v-box
     :height "100%"
     :children [[title] [row-button/panel] [db-printout]]]))
