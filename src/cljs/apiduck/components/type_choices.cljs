(ns apiduck.components.type-choices)

(def choices-array
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

(def choices
  (map (fn [x] {:id x :label x}) choices-array))
