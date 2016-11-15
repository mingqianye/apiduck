(ns apiduck.components.type-choices)

(def choices-array
  [""
   "boolean"
   "number"
   "string"
   "object"
   "array<boolean>"
   "array<number>"
   "array<string>"
   "array<object>"
   "array"])

(def choices
  (map (fn [x] {:id x :label x}) choices-array))
