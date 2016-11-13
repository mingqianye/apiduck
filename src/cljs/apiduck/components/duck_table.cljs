(ns apiduck.duck-table
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [apiduck.duck-row :refer [data-row]]
            [apiduck.colors   :refer [colors]]))

(defn transform-shallow
  [attr-name, attr-schema level]
  (let [gap "\u00A0\u00A0\u00A0"
        indent (reduce str (repeat level gap))
        color  (get colors level)]
    {:id          attr-name
     :sort        attr-name
     :variable    (str indent (name attr-name))
     :title       (str (:title attr-schema) level)
     :description (:description attr-schema)
     :type        (:type attr-schema)
     :level       level
     :color       color
     :block-id    (:block-id attr-schema)
     }))

(defn transform-recursive
  ([attr-schema]
   (transform-recursive :root attr-schema 0))
  ([attr-name attr-schema level]
   (let [cur      (transform-shallow attr-name attr-schema level)
         children (for [[k v] (:properties attr-schema)] (transform-recursive k v (inc level)))]
     (cons cur (flatten children)))))
  
(defn enumerate
  "(for [[item first? last?] (enumerate coll)] ...)  "
  [coll]
  (let [c (dec (count coll))
        f (fn [index item] [item (= 0 index) (= c index)])]
    (map-indexed f coll)))


(defn data-table
  [rows]
  (let [mouse-over (reagent/atom nil)
        click-msg  (reagent/atom "")]
  (fn []
    [:div
      [:table {:class "table table-hover"}
        [:thead          
          [:tr [:th "Sort"] [:th "Actions"] [:th "Variable"] [:th "Title"] [:th "Type"] [:th "Description"] ]
        ]
        [:tbody
          (for [[row first? last?] (enumerate rows)]
            ^{:key (:id row)} [data-row row first? last? mouse-over click-msg])
        ]
      ]
      [:div (str "clicked: " @click-msg)]
    ])))


(defn row-button-demo
  []
  (let [template (re-frame/subscribe [:current-schema])]
    (fn []
      [:div
        [data-table (transform-recursive @template)]
      ])))

;; core holds a reference to panel, so need one level of indirection to get figwheel updates
(defn panel
  []
  [row-button-demo])
