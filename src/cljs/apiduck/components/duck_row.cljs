(ns apiduck.components.duck-row
  (:require [re-com.core :refer [hyperlink row-button md-icon-button]
                         :refer-macros [handler-fn]]
            [reagent.core  :as    reagent]
            [re-frame.core :refer [dispatch]]
            [apiduck.components.type-choices :refer [choices]]
            [apiduck.components.popover-input :refer [popover-input dropdown-input text-input]]))

(defn data-row
  [row]
  (let [mouse-over (reagent/atom false)
        { color :color 
          block-id :block-id 
          title :title
          description :description
          variable :variable 
          indent :indent
          variable-type :type} row]
  (fn []
    (let [mouse-over-row? @mouse-over]
      [:tr {:style {:background-color color}
        :on-mouse-over (handler-fn (reset! mouse-over true))
        :on-mouse-out  (handler-fn (reset! mouse-over false))}
        [:td 
          [row-button
           :md-icon-name    "zmdi zmdi-arrow-back zmdi-hc-rotate-90"
           :mouse-over-row? mouse-over-row?
           :tooltip         "Move this line up"
           :on-click        #(dispatch [:change-click-msg (str "move up" block-id)])
          ]
          [row-button
           :md-icon-name    "zmdi zmdi-arrow-forward zmdi-hc-rotate-90"
           :mouse-over-row? mouse-over-row?
           :tooltip         "Move this line down"
           :on-click        #(dispatch [:change-click-msg (str "move down" block-id)])
          ]] 
       [:td 
         [row-button
          :md-icon-name    "zmdi zmdi-hc-2x mdc-text-green zmdi-plus-square"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Add variables"
          :disabled?       (and mouse-over-row? (not= variable-type "object"))
          :on-click       #(dispatch [:change-click-msg (str "add " block-id)])]
         [row-button
          :md-icon-name    "zmdi zmdi-hc-2x zmdi-copy"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Copy this line"
          :on-click       #(dispatch [:change-click-msg (str "copy " block-id)])]
         [row-button
          :md-icon-name    "zmdi zmdi-hc-2x zmdi-delete mdc-text-red"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Delete this line"
          :on-click       #(dispatch [:change-click-msg (str "delete " block-id)])]
        ]
       [:td [:span indent] [popover-input variable (text-input row :variable)]] 
       [:td [popover-input title (text-input row :title)]] 
       [:td [popover-input variable-type (dropdown-input row :type)]] 
       [:td [popover-input description (text-input row :description)]] 
       ]))))
