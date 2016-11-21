(ns apiduck.default-template)

(def default-schema
  {:variable "root"
   :title "Product"
   :description "A product from Acme's catalog"
   :type "object"
   :children [
      {:variable "id"
       :title "Product id"
       :description "The identifier for a product"
       :type "number"}
      {:variable "name"
       :title "Product name"
       :description "name of a product"
       :type "string"}
      {:variable "price"
       :title "Product price"
       :description "price of a product"
       :type "number"}
      {:variable "address"
       :title "Product address"
       :description "address of a product"
       :type "object"
       :children [
          {:variable "city"
           :title "city name"
           :description "name of the city"
           :type "string"}
          {:variable "country"
           :title "country name"
           :description "name of the country"
           :type "object"
           :children [
              {:variable "code"
               :title "SFDC code"
               :description "SFDC code of the country"
               :type "string"}]}]}]})

(def template
    {:docs [{
      :api-name "name of the api"
      :http-request-type "Post"
      :api-description "description of the api"
      :request-type "post"
      :request-schema default-schema
      :response-schema default-schema
    }]})
