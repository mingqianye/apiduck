(ns apiduck.components.left-side-nav-bar
  (:require [re-com.core :refer [label v-box md-icon-button]
                         :refer-macros [handler-fn]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            ))

(defn nav-item []
  (let [mouse-over? (reagent/atom false)
        current-module-index (re-frame/subscribe [:db :current-module-index])
        current-endpoint-index (re-frame/subscribe [:db :current-endpoint-index])]
    (fn [item]
      (let [text-label     (:label item)
            module-index   (:module-index item)
            endpoint-index (:endpoint-index item)
            selected?      (and (= module-index @current-module-index)
                                (= endpoint-index @current-endpoint-index))
            is-endpoint?   (:is-endpoint item)]
        [:div
         {:style         {:white-space      "nowrap"
                          :line-height      "1.3em"
                          :padding-left     (if-not is-endpoint? "24px" "32px")
                          :padding-top      (if-not is-endpoint? "6px")
                          :font-size        (if-not is-endpoint? "15px")
                          :font-weight      (if-not is-endpoint? "bold")
                          :border-right     (when selected? "4px #d0d0d0 solid")
                          :cursor           (if is-endpoint? "pointer" "default")
                          :color            (if is-endpoint? (when selected? "#111") "#888")
                          :background-color (if (or selected?  @mouse-over?) "#eaeaea")}

          :on-mouse-over (handler-fn (reset! mouse-over? true))
          :on-mouse-out  (handler-fn (reset! mouse-over? false))
          :on-click      (if is-endpoint?
                             (handler-fn (re-frame/dispatch [:change-current-endpoint-index module-index endpoint-index]))) }
         [label
          :label     text-label
          ]
         [md-icon-button
          :md-icon-name    "zmdi zmdi-plus"
          :class           "mdc-text-green"
          :size            :smaller
          :style           (if (or is-endpoint? (not @mouse-over?)) {:display "none"})
          :tooltip         "Add Endpoint"
          :on-click        #(do (re-frame/dispatch [:add-endpoint module-index]) 
                                (.stopPropagation %))
          ]
         [md-icon-button
          :md-icon-name    "zmdi zmdi-delete"
          :class           "mdc-text-red"
          :size            :smaller
          :style           (if (not @mouse-over?) {:display "none"})
          :tooltip         "Delete Endpoint"
          :on-click        #(do (if is-endpoint?
                                  (re-frame/dispatch [:drop-endpoint module-index endpoint-index]) 
                                  (re-frame/dispatch [:drop-module module-index]))
                                (.stopPropagation %))
          ]
         ]))))



(defn left-side-nav-pane []
  (let [items (re-frame/subscribe [:left-nav-bar-items])]
    (fn []
      [:div
        [md-icon-button
          :md-icon-name    "zmdi zmdi-plus-square"
          :class           "mdc-text-green"
          :tooltip         "Add Module"
          :on-click        #(re-frame/dispatch [:add-module])]
        [v-box
         :class    "noselect"
         :style    {:background-color "#fcfcfc"}
         ;:size    "1"
         :children (for [item @items]
                     [nav-item item])]
        ])))
