(ns apiduck.duck-table
  (:require [re-com.core                   :refer [h-box v-box row-button label checkbox p]
                                           :refer-macros [handler-fn]]
            [re-com.util                   :refer [enumerate]]
            [reagent.core                  :as    reagent]))

(defn myrow
  [row first? last? mouse-over click-msg]
  (let [mouse-over-row? (identical? @mouse-over row)]
  [:tr {:on-mouse-over (handler-fn (reset! mouse-over row))
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
       [:td (:name row)] 
       [:td (:from row)] 
       [:td (:to row)] 
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
       ]))

(defn mytable
  [rows]
  (let [mouse-over (reagent/atom nil)
        click-msg  (reagent/atom "")]
  (fn []
    [:div {:class "table-responsive"}
      [:table {:class "table table-hover table-inverse"}
        [:thead          
          [:tr [:th "Sort"] [:th "Name"] [:th "From"] [:th "To"] [:th "Actions"]]
        ]
        [:tbody
          (for [[_ row first? last?] (enumerate (sort-by :sort rows))]
            ^{:key (:id row)} [myrow row first? last? mouse-over click-msg])]
      ]
      [:div (str "clicked: " @click-msg)]
    ])))


(defn row-button-demo
  []
  (let [rows       [{:id "1" :sort 0 :name "Time range 1" :from "18:00" :to "22:30"}
                    {:id "2" :sort 1 :name "Time range 2" :from "18:00" :to "22:30"}
                    {:id "3" :sort 2 :name "Time range 3" :from "06:00" :to "18:00"}]]
    (fn []
      [:div
        [mytable rows]])))


;; core holds a reference to panel, so need one level of indirection to get figwheel updates
(defn panel
  []
  [row-button-demo])
