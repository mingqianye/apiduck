(ns apiduck.views
    (:require [re-frame.core :as re-frame]
              [re-com.core :as re-com]
              [cljs.pprint :refer [pprint]]
              [apiduck.components.table-pane :refer [table-pane]]))

(defn title []
  (let [name (re-frame/subscribe [:endpoint-name])]
    (fn []
      [re-com/title
       :label (str "Hello from " @name)
       :level :level1])))

(defn undo-button
  []
     (fn []
       [:div
       [re-com/button
        :label     "undo"
        :disabled? (not @(re-frame/subscribe [:undos?]))
        :on-click  #(re-frame/dispatch [:undo]) ]
       [re-com/button
        :label     "redo"
        :disabled? (not @(re-frame/subscribe [:redos?]))
        :on-click  #(re-frame/dispatch [:redo]) ]]))

(defn db-printout []
  (let [whole-db (re-frame/subscribe [:whole-db])]
    (fn []
      [:div 
        [:hr]
        [:h3 "Database Atom"]
        [:pre (with-out-str (pprint @whole-db))]])))

(defn refresh_button []
  [re-com/hyperlink
   :label "refresh page"
   ;:on-click #(do (println "refreshing!") (re-frame/dispatch [:handler-with-http]))]
   :on-click #(do (println "refreshing!") (re-frame/dispatch [:change-click-msg "clicked"]))]
  )

(defn main-panel []
  (fn []
    [re-com/v-box
     :height "100%"
     :children [
                [re-com/box :child [title]] 
                [re-com/box :child [refresh_button]] 
                [re-com/box :child [undo-button]] 
                [re-com/line]
                [re-com/box :child [re-com/h-split
                                    :panel-1 [re-com/box :child "My Left Pane"]
                                    :panel-2 [re-com/box :child [table-pane]]
                                    :initial-split 20
                                    ]]
                [re-com/box :child [db-printout]]]]))
