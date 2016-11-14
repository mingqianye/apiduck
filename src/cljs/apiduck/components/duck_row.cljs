(ns apiduck.duck-row
  (:require [re-com.core :refer [row-button checkbox input-text]
                         :refer-macros [handler-fn]]
            [re-frame.core :refer [dispatch]]))

(defn data-row
  [row first? last? mouse-over]
  (let [mouse-over-row? (identical? @mouse-over row)
        color (:color row)
        block-id (:block-id row)]
  [:tr {:style {:background-color color}
        :on-mouse-over (handler-fn (reset! mouse-over row))
        :on-mouse-out  (handler-fn (reset! mouse-over nil))}
        [:td 
          [row-button
           :md-icon-name    "zmdi zmdi-arrow-back zmdi-hc-rotate-90"
           :mouse-over-row? mouse-over-row?
           :tooltip         "Move this line up"
           :disabled?       (and first? mouse-over-row?)
           :on-click        #(dispatch [:change-click-msg (str "move up" block-id)])
          ]
          [row-button
           :md-icon-name    "zmdi zmdi-arrow-forward zmdi-hc-rotate-90"
           :mouse-over-row? mouse-over-row?
           :tooltip         "Move this line down"
           :disabled?       (and last? mouse-over-row?)
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
       [:td (str (:indent row) (:variable row))] 
       [:td [input-text
              :model (:title row)
              :on-change #(dispatch [:change-attr-value block-id :title %])]] 
       [:td (:type row)] 
       [:td (:description row)] 
       ]))
