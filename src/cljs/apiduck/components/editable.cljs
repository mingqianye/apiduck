(ns apiduck.components.editable
  (:require [reagent.core :as reagent]
            [re-frame.core :refer [subscribe]]
            [re-com.core :refer [md-icon-button label input-text hyperlink radio-button]])) 

(defn editable-text [& {:keys [value on-save use-textarea]}]
  (let [edit-mode     (reagent/atom false)
        buffer        (reagent/atom value)]
        
    (fn [& {:keys [value on-save use-textarea enabled]}]
      (if enabled
        (let [element       (if use-textarea :textarea :input)
              save-and-exit #(do (reset! buffer (clojure.string/trim @buffer))
                                 (on-save @buffer) 
                                 (reset! edit-mode false))]
          (if @edit-mode
            [element {:value      @buffer
                      :type       "text"
                      :auto-focus true
                      :on-change  #(reset! buffer (-> % .-target .-value))
                      :on-blur    save-and-exit
                      :on-key-down #(case (.-which %)
                                      13 (if (not= element :textarea) (save-and-exit))
                                      nil)}]
            [:span {:style {:display "inline-block"}}
              [hyperlink 
               :label    value
               :on-click #(do (reset! edit-mode true) (reset! buffer value))]]
          ))
      [:span value]))))
