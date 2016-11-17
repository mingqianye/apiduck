(ns apiduck.components.table-pane
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [re-frame.core :refer [dispatch]]
            [re-com.core :refer [md-icon-button label input-text]
                         :refer-macros [handler-fn]]
            [apiduck.components.popover-input :refer [popover-input]]
            [apiduck.components.duck-table :refer [table]]))

(defn text-input [attr current-value]
  [input-text
    :model current-value
    :change-on-blur? true
    :on-change #(dispatch [:change-doc attr %])])

(defn name-field []
  (let [value (re-frame/subscribe [:name])]
    (fn []
      (let [api-name @value]
      [:div 
       [:div api-name]
       [popover-input api-name (text-input :name api-name)] ]
      ))))

(defn table-pane
  []
  [:div
    [:div [label :label "Name"]]
    [:div [name-field]]
    [label :label "Request"]
    [table :request-schema]
    [:hr]
    [label :label "Response"]
    [table :response-schema]])
