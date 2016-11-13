(ns apiduck.utils)


(defn inject-block-id
  [input-map]
  (if (map? input-map)
    (conj input-map {:block-id (str (random-uuid))})
    input-map))
    
