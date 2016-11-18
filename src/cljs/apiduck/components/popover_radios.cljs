(ns apiduck.components.popover-radios
  (:require [re-com.core :refer [radio-button single-dropdown input-text hyperlink popover-anchor-wrapper popover-content-wrapper]]
            [re-frame.core :refer [dispatch]]
            [apiduck.components.type-choices :refer [choices]]
            [reagent.core  :as    reagent]))

(defn popover-radios
  [& {:keys [value choices on-change]}]
  (let [showing? (reagent/atom false)
        current  (reagent/atom value)]
    (fn []
      [popover-anchor-wrapper
       :showing? showing?
       :position :right-center
       :anchor   [hyperlink
                  :label    value
                  :on-click #(reset! showing? true)]
       :popover  [popover-content-wrapper
                  :body
                    [:div
                      (doall (for [c choices]  
                        ^{:key c}
                        [hyperlink
                         :label       c
                         :style (into {:color "#337ab7"} (if (= c @current) {:font-weight "bold"} {}))
                         :on-click #(do (reset! current c) (on-change c))
                         ]))]
                  :on-cancel #(reset! showing? false)]])))
