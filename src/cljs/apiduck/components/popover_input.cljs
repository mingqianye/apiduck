(ns apiduck.popover-input
  (:require [re-com.core :refer [button popover-anchor-wrapper popover-content-wrapper]]
            [reagent.core  :as    reagent]))

(defn popover-input
  []
  (let [showing? (reagent/atom false)]
    (fn []
      (println showing?)
      [popover-anchor-wrapper
       :showing? showing?
       :position :right-below
       :anchor   [button
                  :label    "Dialog boxa"
                  :on-click #(reset! showing? true)]
       :popover  [popover-content-wrapper
                  :showing? showing?
                  :position :right-below
                  :title    "Title"
                  :body     "Popover body text"]
      ]
      )))
