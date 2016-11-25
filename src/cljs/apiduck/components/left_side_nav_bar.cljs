(ns apiduck.components.left-side-nav-bar
  (:require [re-com.core :refer [v-box]
                         :refer-macros [handler-fn]]
            [reagent.core :as reagent]
            ))

(defn tabs-definition []
  [{:id :buttons1 :level :major :label "Buttons"}
   {:id :buttons2 :level :minor :label "BBBB"}])


(defn nav-item []
  (let [mouse-over? (reagent/atom false)]
    (fn [tab selected-tab-id]
      (let [selected?   (= @selected-tab-id (:id tab))
            is-major?  (= (:level tab) :major)
            has-panel? (some? (:panel tab))]
        [:div
         {:style         {;:width            "150px"
                          :white-space      "nowrap"
                          :line-height      "1.3em"
                          :padding-left     (if is-major? "24px" "32px")
                          :padding-top      (when is-major? "6px")
                          :font-size        (when is-major? "15px")
                          :font-weight      (when is-major? "bold")
                          :border-right     (when selected? "4px #d0d0d0 solid")
                          :cursor           (if has-panel? "pointer" "default")
                          :color            (if has-panel? (when selected? "#111") "#888")
                          :background-color (if (or
                                                  (= @selected-tab-id (:id tab))
                                                  @mouse-over?) "#eaeaea")}

          :on-mouse-over (handler-fn (when has-panel? (reset! mouse-over? true)))
          :on-mouse-out  (handler-fn (reset! mouse-over? false))
          :on-click      (handler-fn (println "on-click"))}
         [:span (:label tab)]]))))

(defn left-side-nav-pane []
    [v-box
     :class    "noselect"
     :style    {:background-color "#fcfcfc"}
     ;:size    "1"
     :children (for [tab (tabs-definition)]
                 [nav-item tab (reagent/atom :buttons1) ])])
