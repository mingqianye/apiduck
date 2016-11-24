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

(defn endpoint-name []
  (let [endpoint-name (re-frame/subscribe [:endpoint-name])]
    (fn []
      [editable-text
       :value @endpoint-name
       :on-save #(dispatch [:change-endpoint :endpoint-name %])])))

(defn http-requests-type []
  (let [request-type (re-frame/subscribe [:http-request-type])]
    (fn []
      [popover-radios
       :value @request-type
       :choices http-request-choices
       :on-change #(dispatch [:change-endpoint :http-request-type %])
       ]
      )))

(defn endpoint-description []
  (let [description (re-frame/subscribe [:endpoint-description])]
    (fn []
      [:div {:style {:white-space "pre-wrap"}}
        [editable-text 
         :value @description
         :use-textarea true
         :on-save #(dispatch [:change-endpoint :endpoint-description %])]
      ])))


(defn table-pane
  []
  [:div
    [:div [label :label "endpoint name"]]
    [endpoint-name]
    [:div [label :label "Http Request Type"]]
    [http-requests-type]
    [:div [label :label "endpoint description"]]
    [endpoint-description]

    [:hr]
    [label :label "Request"]
    [table :request-schema]
    [:hr]
    [label :label "Response"]
    [table :response-schema]])
