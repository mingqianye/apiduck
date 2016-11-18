(ns apiduck.components.table-pane
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [re-frame.core :refer [dispatch]]
            [re-com.core :refer [md-icon-button label input-text hyperlink]
                         :refer-macros [handler-fn]]
            [apiduck.components.popover-input :refer [popover-input]]
            [apiduck.components.editable :refer [editable-text]]
            [apiduck.components.duck-table :refer [table]]))

(defn table-pane
  []
  [:div
    [:div [label :label "Name"]]
    [editable-text 
     :value "text" 
     :on-save #(dispatch [:change-doc :name %]) ]
    [:hr]
    [label :label "Request"]
    [table :request-schema]
    [:hr]
    [label :label "Response"]
    [table :response-schema]])
