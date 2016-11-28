(ns apiduck.components.left-side-nav-bar
  (:require [re-com.core :refer [v-box]
                         :refer-macros [handler-fn]]
            [re-frame.core :as re-frame]
            [reagent.core :as reagent]
            ))

(defn nav-item []
  (let [mouse-over? (reagent/atom false)
        current-module-index (re-frame/subscribe [:db :current-module-index])
        current-endpoint-index (re-frame/subscribe [:db :current-endpoint-index])]
    (fn [item]
      (let [module-index   (:module-index item)
            endpoint-index (:endpoint-index item)
            level          (:level item)
            selected?      (and (= module-index @current-module-index)
                                (= endpoint-index @current-endpoint-index))
            is-major?      (= (:level item) :major)
            has-panel?     (:has-panel item)]
        [:div
         {:style         {:white-space      "nowrap"
                          :line-height      "1.3em"
                          :padding-left     (if is-major? "24px" "32px")
                          :padding-top      (when is-major? "6px")
                          :font-size        (when is-major? "15px")
                          :font-weight      (when is-major? "bold")
                          :border-right     (when selected? "4px #d0d0d0 solid")
                          :cursor           (if has-panel? "pointer" "default")
                          :color            (if has-panel? (when selected? "#111") "#888")
                          :background-color (if (or selected?  @mouse-over?) "#eaeaea")}

          :on-mouse-over (handler-fn (when has-panel? (reset! mouse-over? true)))
          :on-mouse-out  (handler-fn (reset! mouse-over? false))
          :on-click      (if has-panel?
                           (handler-fn (re-frame/dispatch [:change-current-endpoint-index module-index endpoint-index])))}
         [:span (:label item)]]))))



(defn left-side-nav-pane []
  (let [items (re-frame/subscribe [:left-nav-bar-items])]
    (fn []
      [v-box
       :class    "noselect"
       :style    {:background-color "#fcfcfc"}
       ;:size    "1"
       :children (for [item @items]
                   [nav-item item])])))
