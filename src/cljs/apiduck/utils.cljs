(ns apiduck.utils)

(defn new-child []
  {:variable    "(undefined)" 
   :title       "(undefined)" 
   :description "(undefined)" 
   :type        "generic" 
   :collapsed   false
   :block-id    (str (random-uuid))})

(defn new-endpoint []
  { :endpoint-name "(undefined)"
    :http-request-type "GET"
    :endpoint-description "(undefined)"
    :request-schema (new-child)
    :response-schema (new-child)})

(defn new-module []
  {:module-name "(undefined)"
   :endpoints []}
  )


(defn inject-meta
  "recursively inject :block-id and :visible into a nested map"
  [input-map]
  (-> input-map
      (assoc :collapsed false)
      (assoc :block-id (str (random-uuid)))
      (update :children #(map inject-meta %))))

(defn change-block
 [input-map block-id attr new-value]
  (if (= block-id (:block-id input-map))
    (assoc input-map attr new-value)
    (into input-map
          {:children (for [c (:children input-map)] (change-block c block-id attr new-value))})))

(defn change-block-type
 [input-map block-id new-type]
  (if (= block-id (:block-id input-map))
    (if (not= new-type "object")
      (assoc (dissoc input-map :children) :type new-type)
      (assoc input-map :type new-type))
    (assoc input-map
           :children (for [c (:children input-map)] (change-block-type c block-id new-type)))))

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


(defn cook-project [project]
  (let [cook-endpoint (fn [endpoint] (-> endpoint
                                         (update :request-schema inject-meta)
                                         (update :response-schema inject-meta)))
        cook-module (fn [module] (-> module 
                                     (update :endpoints #(vec (map cook-endpoint %)))))]
    (-> project
        (update :modules #(vec (map cook-module %))))))

(defn current-endpoint [db]
  (let [module-index (:current-module-index db)
        endpoint-index (:current-endpoint-index db)]
    (get-in db [:project :modules module-index :endpoints endpoint-index])))

(defn remove-nth [index coll]
  (vec (concat
          (take index coll)
          (drop (inc index) coll))))
