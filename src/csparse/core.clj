(ns csparse.core
  [require [pl.danieljanus.tagsoup :as ts]
           [clojure.walk :as walk]
   ])

(def linkmap (atom {}))

(defn my-fn [l]
  (let [firstl (if (vector? l) (first l))
        p1 (if (and (= :a firstl)
                    (:href (second l))
                    (:title (second l))
                )
                (swap! linkmap assoc (last l) (:href (second l)))
               ;;     (println (str (last l) "-->" (:href (second l))))
                )
        ]
    l))

(defn -main
  [& args]
  (def cheatsheet (ts/parse "https://clojure.org/api/cheatsheet"))
  (println cheatsheet)
  (walk/postwalk my-fn cheatsheet)
 ; (println @linkmap)
 (doall (map (fn [[k v]] (println (str "\"" k "\"  \""  v "\""))) @linkmap))
)
