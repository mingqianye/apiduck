(ns apiduck.components.duck-table
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [apiduck.components.duck-row :refer [data-row]]
            [apiduck.components.colors   :refer [colors]]))

(defn sort-by-variable
  [input-map]
  (let [children     (:children input-map)
        sorted       (sort-by :variable children)
        new-children (map sort-by-variable sorted)]
    (into input-map {:children new-children})))

(defn transform-shallow
  [schema level]
  (let [gap "\u00A0\u00A0\u00A0"]
    {:variable    (:variable schema)
     :title       (:title schema)
     :description (:description schema)
     :type        (:type schema)
     :level       level
     :color       (get colors level)
     :block-id    (:block-id schema)
     :indent      (reduce str (repeat level gap))
     }))

(defn transform-recursive
  ([schema]
   (transform-recursive schema 0))
  ([schema level]
   (let [cur      (transform-shallow schema level)
         children (for [v (:children schema)] (transform-recursive v (inc level)))]
     (cons cur (flatten children)))))
  
(defn data-table
  [rows]
  [:table {:class "table"}
    [:thead          
      [:tr [:th "Actions"] [:th "Variable"] [:th "Title"] [:th "Type"] [:th "Description"] ]
    ]
    [:tbody
     (map (fn [x] (with-meta [(data-row x)] {:key x})) rows)
     ;(for [r rows] (with-meta [(data-row r)] {:key r}))
    ]])


(defn row-button-demo
  []
  (let [template (re-frame/subscribe [:current-schema])]
    (fn []
      [:div
        [data-table (transform-recursive (sort-by-variable @template))]
      ])))

;; core holds a reference to panel, so need one level of indirection to get figwheel updates
(defn table
  []
  [row-button-demo])
