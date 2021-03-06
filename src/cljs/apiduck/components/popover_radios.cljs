(ns apiduck.components.popover-radios
  (:require [re-com.core :refer [radio-button single-dropdown input-text hyperlink popover-anchor-wrapper popover-content-wrapper]]
            [re-frame.core :refer [subscribe]]
            [reagent.core  :as    reagent]))

(defn popover-radios
  [& {:keys [value choices on-change]}]
  (let [showing? (reagent/atom false)
        current  (reagent/atom value)]
    (fn [& {:keys [value choices on-change enabled]}]
      (if enabled
        [popover-anchor-wrapper
         :showing? showing?
         :position :right-center
         :anchor   [hyperlink
                    :label    value
                    :on-click #(do (reset! showing? true) (reset! current value))]
         :popover  [popover-content-wrapper
                    :body
                      [:div
                        (doall (for [c choices]  
                          ^{:key c}
                          [hyperlink
                           :label       c
                           :style (into {:color "#337ab7"} (if (= c @current) {:font-weight "bold"} {}))
                           :on-click #(do (reset! current c) (on-change c) (reset! showing? false))
                           ]))]
                    :on-cancel #(reset! showing? false)]]
        [:span value]))))
