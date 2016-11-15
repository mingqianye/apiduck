(ns apiduck.db
  (:require [apiduck.utils :refer [inject-block-ids]]))

(def default-template2
  {"title" "1"
   "description" "2"
   "type" "3"})

(def default-template
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

(def default-template3
  {
  "title" "Product"
  "description" "A product from Acme's catalog"
  "type" "object"
  "properties" {
      "id"    { "title" "Product id" 
                "description" "The identifier for a product" 
                "type" "number" }
      "name"  { "title" "Product name" 
                "description" "Name of the product" 
                "type" "string" }
      "price" { "title" "Product price" 
                "description" "The price" 
                "type" "number" }

      "address" { "title" "Product address" 
                  "description" "The address of the product shipping address"
                  "type" "object"
                  "properties" {
                     "city" {
                        "title" "City name"
                        "description" "The name of the address city"
                        "type" "string"
                            }
                     "country"  {
                        "title" "Country name"
                        "description" "The name of address country"
                        "type" "string"
                        "properties" {
                          "code" {
                                  "title" "SFDC code"
                                  "description" "SFDC code of the country"
                                  "type" "string"
                                 }
                          }
                                }
                               }
                }

  }
  })

(defn prepare-schema
  [raw-json]
  (-> raw-json
      inject-block-ids))

(def default-db
  {
    :name "re-frame"
    :clicked-msg "no click yet"
    :current-schema (prepare-schema default-template)
  })
