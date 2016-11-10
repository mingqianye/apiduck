(ns apiduck.db)

(def default-template
  {
  "title" "Product"
  "description" "A product from Acme's catalog"
  "type" "object"
  "properties" {
      "id"    { "description" "The identifier for a product" :type "integer" }
      "name"  { "description" "Name of the product" :type "string" }
      "price" { "type" "number" }
  }
  })

(def default-db
  {
    :name "re-frame"
    :default-template default-template
  })
