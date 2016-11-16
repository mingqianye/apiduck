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
  [schema level visible]
  (let [gap "\u00A0\u00A0\u00A0"]
    {:variable    (:variable schema)
     :title       (:title schema)
     :description (:description schema)
     :type        (:type schema)
     :level       level
     :color       (get colors level)
     :block-id    (:block-id schema)
     :indent      (reduce str (repeat (dec level) gap))
     :visible     visible
     :collapsed   (:collapsed schema)
     }))

(defn transform-recursive
  ([schema]
   (transform-recursive schema 0 true))
  ([schema level visible]
   (let [cur           (transform-shallow schema level visible)
         child-visible (and visible (not (:collapsed schema)))
         children      (for [v (:children schema)] 
                         (transform-recursive v (inc level) child-visible))]
     (cons cur (flatten children)))))

(defn add-id
  [rows]
  (map-indexed (fn [i c] (assoc c :id i)) rows))

(defn to-data-rows
  [rows]
  (for [r rows] ^{:key r} [data-row r]))
  
(defn data-table
  [rows]
  [:table {:class "table"}
    [:thead          
      [:tr [:th] [:th "#"] [:th "Actions"] [:th "Variable"] [:th "Title"] [:th "Type"] [:th "Description"]]]
    [:tbody rows ]])

(defn table
  []
  (let [template (re-frame/subscribe [:current-schema])]
    (fn []
        (-> @template
              sort-by-variable    ; sort children recursively
              transform-recursive ; transform children recursively and flatten to array
              add-id              ; add row id 0,1,2...
              rest                ; hide row 0
              to-data-rows        ; add React meta data, transform to <tr> elements
              data-table)         ; wrap <tr> elements in <table>
      )))
