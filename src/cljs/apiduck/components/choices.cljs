(ns apiduck.components.choices)

(def variable-type-choices
  ["generic"
   "boolean"
   "number"
   "string"
   "object"
   "array<boolean>"
   "array<number>"
   "array<string>"
   "array<object>"
   "array<generic>"])

(def http-request-choices
  ["Get" "Post" "Put" "Delete" ])

;(def choices
;  (map (fn [x] {:id x :label x}) choices-array))
