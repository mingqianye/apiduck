(ns apiduck.components.table-pane
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [re-com.core :refer [md-icon-button]
                         :refer-macros [handler-fn]]
            [apiduck.components.duck-table :refer [table]]))

(defn add-root-element-button
  []
  (let [hover? (reagent/atom false)]
  (fn []
    [md-icon-button
      :md-icon-name    "zmdi zmdi-plus-square"
      :class           "mdc-text-green"
      :tooltip         "Add Property"
      :on-click        #()] )))

(defn table-pane
  []
  [:div
    [table]
    [add-root-element-button]
  ]
  )
