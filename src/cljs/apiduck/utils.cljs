(ns apiduck.utils)

(defn inject-block-ids
  "recursively inject uuid into a nested map"
  [input-map]
  (into input-map 
        {:block-id (str (random-uuid)) 
         :children (map inject-block-ids (:children input-map))}))

(defn change-block
  [input-map block-id attr new-value]
  (if (= block-id (:block-id input-map))
    (assoc input-map attr new-value)
    (into input-map
          {:children (for [c (:children input-map)] (change-block c block-id attr new-value))})))
