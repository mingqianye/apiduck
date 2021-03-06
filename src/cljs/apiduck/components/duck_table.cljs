(ns apiduck.components.duck-table
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [re-com.core :refer [md-icon-button]]
            [re-frame.core :refer [dispatch]]
            [apiduck.components.duck-row :refer [data-row]]
            [apiduck.components.colors   :refer [colors]]))

(defn transform-shallow
  [schema level visible]
  (let [gap "\u00A0\u00A0\u00A0"]
    {:variable      (:variable schema)
     :title         (:title schema)
     :description   (:description schema)
     :variable-type (:type schema)
     :level         level
     :color         (get colors level)
     :block-id      (:block-id schema)
     :indent        (reduce str (repeat (dec level) gap))
     :visible       visible
     :collapsed     (:collapsed schema)
     :expandable    (and (= "object" (:type schema)) (not-empty (:children schema)))
     }))

(defn transform-recursive
  ([schema]
   (transform-recursive schema 0 true))
  ([schema level visible]
   (let [cur           (transform-shallow schema level visible)
         child-visible (and visible (not (:collapsed schema)))
         children      (for [v (sort-by :variable (:children schema))] ; sort children first
                         (transform-recursive v (inc level) child-visible))]
     (cons cur (flatten children)))))

(defn add-id-and-schema-type
  [rows schema-type]
  (let [inject (fn [index row] (into row {:id index :schema-type schema-type}))]
    (map-indexed inject rows)))

(defn to-data-rows [rows]
  (for [r rows] ^{:key r} [data-row r]))


(defn data-table [rows]
  (println "data-table")
  [:table {:class "table"}
    [:thead 
      [:tr [:th] [:th "#"] [:th "Actions"] [:th "Variable"] [:th "Title"] [:th "Type"] [:th "Description"]]]
    [:tbody rows ]])

(defn build-rows [root schema-type]
  (-> root
    transform-recursive    ; transform children recursively and flatten to array
    (add-id-and-schema-type schema-type); add row id 0,1,2...
    rest                   ; hide row 0 (the root)
    to-data-rows           ; add React meta data, transform to <tr> elements
  ))

(defn build-head []
  (let [in-edit-mode? @(re-frame/subscribe [:in-edit-mode])]
  [:tr 
   [:th] 
   [:th "#"] 
   [:th 
      {:style {:display (if (= in-edit-mode? false) "none")}}
      "Actions"] 
   [:th "Variable"] 
   [:th "Title"]
   [:th "Type"]
   [:th "Description"]]))


(defn table
  [schema-type]
  (let [root (re-frame/subscribe [:current-endpoint schema-type])]
    (fn []
      [:div
        [md-icon-button
          :md-icon-name    "zmdi zmdi-plus-square"
          :class           "mdc-text-green"
          :tooltip         "Add Property"
          :on-click        #(dispatch [:add-row schema-type (:block-id @root)])]
        [:table {:class "table"}
          [:thead (build-head) ]
          [:tbody (build-rows @root schema-type) ]]
        ])))
