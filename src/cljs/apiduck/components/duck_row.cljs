(ns apiduck.duck-row
  (:require [re-com.core                   :refer [row-button checkbox]
                                           :refer-macros [handler-fn]]))

(defn data-row
  [row first? last? mouse-over click-msg]
  (let [mouse-over-row? (identical? @mouse-over row)
        color (:color row)]
  [:tr {:style {:background-color color}
        :on-mouse-over (handler-fn (reset! mouse-over row))
        :on-mouse-out  (handler-fn (reset! mouse-over nil))}
        [:td 
          [row-button
                          :md-icon-name    "zmdi zmdi-arrow-back zmdi-hc-rotate-90"
                          :mouse-over-row? mouse-over-row?
                          :tooltip         "Move this line up"
                          :disabled?       (and first? mouse-over-row?)
                          :on-click        #(reset! click-msg (str "move row " (:id row) " up"))
          ]
          [row-button
                           :md-icon-name    "zmdi zmdi-arrow-forward zmdi-hc-rotate-90"
                           :mouse-over-row? mouse-over-row?
                           :tooltip         "Move this line down"
                           :disabled?       (and last? mouse-over-row?)
                           :on-click        #(reset! click-msg (str "move row " (:id row) " down"))
          ]] 
       [:td 
         [row-button
          :md-icon-name    "zmdi zmdi-copy"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Copy this line"
          :on-click        #(reset! click-msg (str "copy row " (:id row)))]
         [row-button
          :md-icon-name    "zmdi zmdi-edit"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Edit this line"
          :on-click        #(reset! click-msg (str "edit row " (:id row)))]
         [row-button
          :md-icon-name    "zmdi zmdi-delete"
          :mouse-over-row? mouse-over-row?
          :tooltip         "Delete this line"
          :on-click        #(reset! click-msg (str "delete row " (:id row)))]
        ]
       [:td (:variable row)] 
       [:td (:title row)] 
       [:td (:type row)] 
       [:td (:description row)] 
       ]))
