(ns apiduck.duck-table
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [apiduck.duck-row :refer [data-row]]))

(defn myrows 
  [template]
   (for [[k v] (template "properties")] 
     {:id k 
      :sort k
      :variable k 
      :description (v "description") 
      :type (v "type")}))

(defn enumerate
  "(for [[item first? last?] (enumerate coll)] ...)  "
  [coll]
  (let [c (dec (count coll))
        f (fn [index item] [item (= 0 index) (= c index)])]
    (->> coll
         (sort-by :sort)
         (map-indexed f))))


(defn data-table
  [rows]
  (let [mouse-over (reagent/atom nil)
        click-msg  (reagent/atom "")]
  (fn []
    [:div
      [:table {:class "table table-hover"}
        [:thead          
          [:tr [:th "Sort"] [:th "Name"] [:th "From"] [:th "To"] [:th "Actions"]]
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
  (let [template (re-frame/subscribe [:default-template])]
    (fn []
      [:div
        [data-table (myrows @template)]
      ])))

;; core holds a reference to panel, so need one level of indirection to get figwheel updates
(defn panel
  []
  [row-button-demo])
