(ns apiduck.db)

(def default-template
  {
  "title" "Product"
  "description" "A product from Acme's catalog"
  "type" "object"
  "properties" {
      "id"    { "title" "Product id" 
                "description" "The identifier for a product" 
                "type" "integer" }
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
                                }
                               }
                }

  }
  })

(def default-db
  {
    :name "re-frame"
    :current-schema default-template
  })
