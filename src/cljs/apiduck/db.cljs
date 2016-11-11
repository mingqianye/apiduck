(ns apiduck.db)

(def default-template
  {
  "title" "Product"
  "description" "A product from Acme's catalog"
  "type" "object"
  "properties" {
      "id"    { "title" "Product id" "description" "The identifier for a product" "type" "integer" }
      "name"  { "title" "Product name" "description" "Name of the product" "type" "string" }
      "price" { "title" "Product price" "description" "The price" "type" "number" }
  }
  })

(def default-db
  {
    :name "re-frame"
    :current-schema default-template
  })
