(ns apiduck.popover-input
  (:require [re-com.core :refer [single-dropdown input-text hyperlink popover-anchor-wrapper popover-content-wrapper]]
            [re-frame.core :refer [dispatch]]
            [apiduck.type-choices :refer [choices]]
            [reagent.core  :as    reagent]))

(defn text-input [row attr]
  [input-text
    :model (attr row)
    :change-on-blur? true
    :on-change #(dispatch [:change-attr-value (:block-id row) attr %])])

(defn dropdown-input [row attr]
  [single-dropdown
   :choices choices
   :model (attr row)
   :on-change #(dispatch [:change-attr-value (:block-id row) attr %])])

(defn popover-input
  [text popover-body]
  (let [showing? (reagent/atom false)]
    (fn []
      [popover-anchor-wrapper
       :showing? showing?
       :position :below-center
       :anchor   [hyperlink
                  :label    text
                  :on-click #(reset! showing? true)]
       :popover  [popover-content-wrapper
                  :body     popover-body
                  :on-cancel #(reset! showing? false)]])))
