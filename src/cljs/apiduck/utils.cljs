(ns apiduck.utils)

(defn inject-block-ids
  "recursively inject uuid into a nested map when :title is found in the map"
  [input-map]
  (cond
    (not (map? input-map)) input-map
    (contains? input-map :title) (into {:block-ids (str (random-uuid))} (for [[k v] input-map] {k (inject-block-ids v)}))
    :else (into {} (for [[k v] input-map] {k (inject-block-ids v)}))))
