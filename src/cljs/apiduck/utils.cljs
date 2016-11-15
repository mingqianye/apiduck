(ns apiduck.utils)

(defn new-child []
  {:variable    "(undefined)" 
   :title       "(undefined)" 
   :description "(undefined)" 
   :type        "generic" 
   :block-id    (str (random-uuid))})


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

(defn drop-block
  [input-map block-id]
  (let [is-target    (fn [m] (= block-id (:block-id m)))
        new-children (remove is-target (:children input-map))]
    (into input-map 
          {:children (for [c new-children] (drop-block c block-id))})))

(defn add-block
  [input-map block-id]
  (if (= block-id (:block-id input-map))
    (into input-map {:children (cons (new-child) (:children input-map))})
    (into input-map {:children (for [c (:children input-map)] (add-block c block-id))})))
