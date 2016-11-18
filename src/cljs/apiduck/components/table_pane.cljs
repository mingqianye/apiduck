(ns apiduck.components.table-pane
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [re-frame.core :refer [dispatch]]
            [re-com.core :refer [md-icon-button label input-text hyperlink]
                         :refer-macros [handler-fn]]
            [apiduck.components.popover-radios :refer [popover-radios]]
            [apiduck.components.editable :refer [editable-text]]
            [apiduck.components.choices :refer [http-request-choices]]
            [apiduck.components.duck-table :refer [table]]))

(defn api-name []
  (let [api-name (re-frame/subscribe [:api-name])]
    (fn []
      [editable-text 
       :value @api-name
       :on-save #(dispatch [:change-doc :api-name %])])))

(defn http-requests-type []
  (let [request-type (re-frame/subscribe [:http-request-type])]
    (fn []
      [popover-radios
       :value @request-type
       :choices http-request-choices
       :on-change #(dispatch [:change-doc :http-request-type %])
       ]
      )))

(defn api-description []
  (let [description (re-frame/subscribe [:api-description])]
    (fn []
      [:div {:style {:white-space "pre-wrap"}}
        [editable-text 
         :value @description
         :use-textarea true
         :on-save #(dispatch [:change-doc :api-description %])]
      ])))


(defn table-pane
  []
  [:div
    [:div [label :label "Name"]]
    [api-name]
    [:div [label :label "Http Request Type"]]
    [http-requests-type]
    [:div [label :label "API description"]]
    [api-description]

    [:hr]
    [label :label "Request"]
    [table :request-schema]
    [:hr]
    [label :label "Response"]
    [table :response-schema]])
