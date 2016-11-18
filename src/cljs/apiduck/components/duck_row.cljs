(ns apiduck.components.duck-row
  (:require [re-com.core :refer [radio-button hyperlink row-button md-icon-button input-text single-dropdown]
                         :refer-macros [handler-fn]]
            [reagent.core  :as    reagent]
            [re-frame.core :refer [dispatch]]
            [apiduck.components.choices :refer [variable-type-choices]]
            [apiduck.components.editable :refer [editable-text]]
            [apiduck.components.popover-radios :refer [popover-radios]]
            ))

(defn data-row
  [row]
  (let [mouse-over (reagent/atom false)
        { :keys [color         
                 block-id      
                 title         
                 description   
                 variable      
                 indent        
                 variable-type 
                 visible       
                 collapsed     
                 expandable
                 schema-type] } row]
  (fn []
    (let [mouse-over-row? @mouse-over]
      [:tr {:style {:display (if (not= visible true) "none")}}
       [:td 
         [md-icon-button
          :md-icon-name    (if collapsed "zmdi zmdi-plus" "zmdi zmdi-minus")
          :size            :smaller
          :tooltip         (if collapsed "Expand" "Collapse")
          :style           (if (not expandable) {:display "none"})
          :on-click        #(dispatch [:collapse-row schema-type block-id (not collapsed)])
          ]
        ]
       [:td (:id row)]
       [:td 
        {:style         {:background-color "#F7F7F7"}
         :on-mouse-over (handler-fn (reset! mouse-over true))
         :on-mouse-out  (handler-fn (reset! mouse-over false))}
         [md-icon-button
          :md-icon-name    "zmdi zmdi-delete"
          :class           "mdc-text-red"
          :size            :smaller
          :style           (if (not mouse-over-row?) {:display "none"})
          :tooltip         "Delete Property"
          :on-click        #(dispatch [:drop-row schema-type block-id])
          ]
         [md-icon-button
          :md-icon-name    "zmdi zmdi-plus"
          :class           "mdc-text-green"
          :size            :smaller
          :style           (if (or (not mouse-over-row?) (not= variable-type "object")) {:display "none"})
          :tooltip         "Add Property"
          :on-click        #(dispatch [:add-row schema-type block-id])]
        ]
       [:td {:style {:background-color color}} 
        [:span indent] 
        [editable-text 
         :value variable 
         :on-save #(dispatch [:change-attr-value schema-type block-id :variable %])]] 
       [:td {:style {:background-color color}} 
        [editable-text 
         :value title
         :on-save #(dispatch [:change-attr-value schema-type block-id :title %])]] 
       [:td {:style {:background-color color}} 
        [popover-radios
         :value variable-type
         :choices variable-type-choices
         :on-change #(dispatch [:change-attr-value schema-type block-id :type %])
         ]] 
       [:td {:style {:background-color color}} 
        [editable-text 
         :value description
         :on-save #(dispatch [:change-attr-value schema-type block-id :description %])]] 
       ]))))
