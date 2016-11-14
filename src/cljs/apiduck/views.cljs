(ns apiduck.views
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]
              [apiduck.duck-table :as duck-table]))

(defn title []
  (let [name (re-frame/subscribe [:name])]
    (fn []
      [re-com/title
       :label (str "Hello from " @name)
       :level :level1])))

(defn undo-button
  []
     (fn []
       [:div
       [re-com/button
        :label "undo"
        :disabled? (not @(re-frame/subscribe [:undos?]))
        :on-click #(re-frame/dispatch [:undo]) ]
       [re-com/button
        :label "redo"
        :disabled? (not @(re-frame/subscribe [:redos?]))
        :on-click #(re-frame/dispatch [:redo]) ]]))

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
     :children [
                [re-com/box :child [title]] 
                [re-com/box :child [undo-button]] 
                [re-com/h-box  :children [
                                          [re-com/gap :size "15px"]
                                          [re-com/box :child "My Left Pane"]
                                          [re-com/gap :size "15px"]
                                          [re-com/box :child [duck-table/panel]]
                                         ]]
                [re-com/box :child [db-printout]]]]))
