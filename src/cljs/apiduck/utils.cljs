(ns apiduck.utils)

(defn inject-block-ids
  "recursively inject uuid into a nested map when :title is found in the map"
  [input-map]
  (cond
    (not (map? input-map)) input-map
    (contains? input-map :title) (into {:block-id (str (random-uuid))} (for [[k v] input-map] {k (inject-block-ids v)}))
    :else (into {} (for [[k v] input-map] {k (inject-block-ids v)}))))

(defn change-block
  [input-map block-id attr new-value]
  (cond
    (not (map? input-map)) input-map
    (= (:block-id input-map) block-id) (assoc input-map attr new-value)
    :else (into {} (for [[k v] input-map] {k (change-block v block-id attr new-value)}))))
