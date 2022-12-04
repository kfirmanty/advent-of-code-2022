(ns advent-of-code-2022.day4
  (:require [clojure.string :as string]))

(defn parse-input []
  (->> (string/split (slurp "input/day4") #"\n")
       (map (fn [l]
              (->>
               (string/split (string/replace l #"[-,]" " ") #"\s+")
               (map #(Integer/parseInt %))
               (partition 2))))))

(defn pair-contained? [[[min1 max1] [min2 max2]]]
  (or (<= min2 min1 max1 max2)
      (<= min1 min2 max2 max1)))

(defn solve-1 []
  (->> (parse-input)
       (filter pair-contained?)
       count))

(defn pair-overlaps? [[[min1 max1] [min2 max2]]]
  (and (>= max1 min2)
       (<= min1 max2)))

(defn solve-2 []
  (->> (parse-input)
       (filter pair-overlaps?)
       count))
