(ns apiduck.duck-table
  (:require [reagent.core                  :as    reagent]
            [apiduck.duck-row              :refer [data-row]]))

(defn enumerate
  "(for [[item first? last?] (enumerate coll)] ...)  "
  [coll]
  (let [c (dec (count coll))
        f (fn [index item] [item (= 0 index) (= c index)])
        ]
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
  (let [rows       [{:id "1" :sort 0 :name "Time range 1" :from "18:00" :to "22:30"}
                    {:id "2" :sort 1 :name "Time range 2" :from "18:00" :to "22:30"}
                    {:id "3" :sort 2 :name "Time range 3" :from "06:00" :to "18:00"}]]
    (fn []
      [:div
        [data-table rows]])))


;; core holds a reference to panel, so need one level of indirection to get figwheel updates
(defn panel
  []
  [row-button-demo])
