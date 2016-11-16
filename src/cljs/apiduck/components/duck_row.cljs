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
        { color         :color
          block-id      :block-id
          title         :title
          description   :description
          variable      :variable
          indent        :indent
          variable-type :type
          visible       :visible
          collapsed     :collapsed} row]
  (fn []
    (let [mouse-over-row? @mouse-over]
      [:tr {:style {:display (if (not= visible true) "none")}}
       [:td 
         [md-icon-button
          :md-icon-name    (if collapsed "zmdi zmdi-plus" "zmdi zmdi-minus")
          :tooltip         (if collapsed "Expand" "Collapse")
          :style           (if (not= variable-type "object") {:display "none"})
          :on-click        #(dispatch [:collapse-row block-id (not collapsed)])
          ]
        ]
       [:td (:id row)]
       [:td 
        {:style         {:background-color "#F7F7F7"}
         :on-mouse-over (handler-fn (reset! mouse-over true))
         :on-mouse-out  (handler-fn (reset! mouse-over false))}
         [row-button
          :md-icon-name    "zmdi zmdi-delete"
          :class           "mdc-text-red"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Delete Property"
          :on-click        #(dispatch [:drop-row block-id])
          ]
         [row-button
          :md-icon-name    "zmdi zmdi-plus-square"
          :class           "mdc-text-green"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Add Property"
          :style           (if (not= variable-type "object") {:display "none"})
          :on-click        #(dispatch [:add-row block-id])]
        ]
       [:td {:style {:background-color color}} [:span indent] [popover-input variable (text-input row :variable)]] 
       [:td {:style {:background-color color}} [popover-input title (text-input row :title)]] 
       [:td {:style {:background-color color}} [popover-input variable-type (dropdown-input row :type)]] 
       [:td {:style {:background-color color}} [popover-input description (text-input row :description)]] 
       ]))))
