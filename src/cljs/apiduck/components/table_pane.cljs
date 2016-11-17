(ns apiduck.components.table-pane
  (:require [re-frame.core    :as re-frame]
            [reagent.core     :as    reagent]
            [re-frame.core :refer [dispatch]]
            [re-com.core :refer [md-icon-button label input-text hyperlink]
                         :refer-macros [handler-fn]]
            [apiduck.components.popover-input :refer [popover-input]]
            [apiduck.components.duck-table :refer [table]]))

(defn text-input [attr current-value]
  [input-text
    :model current-value
    :change-on-blur? true
    :on-change #(dispatch [:change-doc attr %])])


(defn editable-text []
  (let [edit-mode (reagent/atom false)
        text (reagent/atom "asdf")]
    (fn []
      (println "here")
       (if @edit-mode
         ;[input-text
         ; :model @text
         ; :change-on-blur? true
         ; :on-change #(do (reset! text %) (reset! edit-mode false))]
         [:input {:auto-focus true :type "text", :name "title", :id "title", :value @text
                  :on-change #(reset! @text %) :on-blur #(print "b")}]
         [hyperlink
          :label @text
          :on-click #(reset! edit-mode true)])
    )))

(defn todo-edit []
  (with-meta editable-text
   {:component-did-mount #(.focus (reagent/dom-node %))}))

(defn name-field []
  (let [value (re-frame/subscribe [:name])]
    (fn []
      (let [api-name @value]
      [:div 
       [:div api-name]
       [popover-input api-name (text-input :name api-name)] ]
      ))))

(defn table-pane
  []
  [:div
    [:div [label :label "Name"]]
    ;[:div [name-field]]
    ;[:div [editable-text]]
    ;[:div [:input {:type "text", :name "title", :id "title", :value nil}]]
    [label :label "Request"]
    [table :request-schema]
    [:hr]
    [label :label "Response"]
    [table :response-schema]])
