(ns apiduck.components.popover-input
  (:require [re-com.core :refer [single-dropdown input-text hyperlink popover-anchor-wrapper popover-content-wrapper]]
            [re-frame.core :refer [dispatch]]
            [apiduck.components.type-choices :refer [choices]]
            [reagent.core  :as    reagent]))

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
