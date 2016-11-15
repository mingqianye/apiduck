(ns apiduck.duck-row
  (:require [re-com.core :refer [hyperlink row-button]
                         :refer-macros [handler-fn]]
            [reagent.core  :as    reagent]
            [re-frame.core :refer [dispatch]]
            [apiduck.type-choices :refer [choices]]
            [apiduck.popover-input :refer [popover-input dropdown-input text-input]]))

(defn data-row
  [row]
  (let [mouse-over (reagent/atom false)
        color (:color row)
        block-id (:block-id row)]
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
          :md-icon-name    "zmdi zmdi-copy"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Copy this line"
          :on-click       #(dispatch [:change-click-msg (str "copy" block-id)])]
         [row-button
          :md-icon-name    "zmdi zmdi-edit"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Edit this line"
          :on-click       #(dispatch [:change-click-msg (str "edit" block-id)])]
         [row-button
          :md-icon-name    "zmdi zmdi-delete"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Delete this line"
          :on-click       #(dispatch [:change-click-msg (str "delete " block-id)])]
        ]
       [:td [hyperlink :label (str (:indent row) (:variable row))]]
       [:td [popover-input (:title row) (text-input row :title)]] 
       [:td [popover-input (:type row) (dropdown-input row :type)]] 
       [:td [popover-input (:description row) (text-input row :description)]] 
       ]))))
