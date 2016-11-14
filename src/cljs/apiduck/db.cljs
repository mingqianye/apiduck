(ns apiduck.db
  (:require [apiduck.utils :refer [inject-block-ids]]))

(def default-template2
  {"title" "1"
   "description" "2"
   "type" "3"})

(def default-template
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
      clojure.walk/keywordize-keys
      inject-block-ids))

(def default-db
  {
    :name "re-frame"
    :clicked-msg "no click yet"
    :current-schema (prepare-schema default-template)
  })
