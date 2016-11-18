(ns apiduck.components.editable
  (:require [reagent.core :as reagent]
            [re-com.core :refer [md-icon-button label input-text hyperlink radio-button]])) 

(defn editable-text
  [& {:keys [value on-save]}]
  (let [text          (reagent/atom value)
        edit-mode     (reagent/atom false)
        save-and-exit #(do (reset! text (clojure.string/trim @text))
                           (on-save @text) 
                           (reset! edit-mode false))]
        
    (fn []
      (if @edit-mode
        [:input {:value      @text
                 :type       "text"
                 :auto-focus true
                 :on-change  #(reset! text (-> % .-target .-value))
                 :on-blur    save-and-exit
                 :on-key-down #(case (.-which %)
                                 13 (save-and-exit)
                                 nil)}]
        [:span {:style {:display "inline-block"}}
        [hyperlink 
         :label    @text 
         :on-click #(reset! edit-mode true)]]
        ))))
