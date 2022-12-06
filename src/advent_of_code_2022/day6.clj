(ns advent-of-code-2022.day6)

(defn parse-input []
  (slurp "input/day6"))

(defn solve [input char-count]
  (->> input
       (partition-all char-count 1)
       (map-indexed (fn [i el]
                      (when (= (count (into #{} el)) char-count)
                        i)))
       (filter some?)
       first
       (+ char-count)))

(defn solve-1 []
  (solve (parse-input) 4))

(defn solve-2 []
  (solve (parse-input) 14))
