(ns apiduck.utils)

(defn new-child []
  {:variable    "(undefined)" 
   :title       "(undefined)" 
   :description "(undefined)" 
   :type        "generic" 
   :collapsed   false
   :block-id    (str (random-uuid))})


(defn inject-meta
  "recursively inject :block-id and :visible into a nested map"
  [input-map]
  (into input-map 
        {:collapsed  false
         :block-id   (str (random-uuid)) 
         :children   (map inject-meta (:children input-map))}))

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

(defn collapse-block
  [input-map block-id value]
  (if (= block-id (:block-id input-map))
    (into input-map {:collapsed value})
    (into input-map {:children (for [c (:children input-map)] (collapse-block c block-id value))})))
