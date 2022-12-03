(ns advent-of-code-2022.day3
  (:require [clojure.string :as string]
            [clojure.set :as cset]))

(defn parse-input []
  (-> "input/day3"
      slurp
      (string/split #"\n")))

(defn rs->priority [rs]
  (mapcat (fn [els]
            (map (fn [el]
                   (let [i (int el)]
                     (if (<= 65 i 90)
                       (- i 38)
                       (- i 96))))
                 els))
          rs))

(defn solve-1 []
  (let [rs (parse-input)]
    (->> rs
         (map (fn [rs]
                [(take (/ (count rs) 2) rs)
                 (take-last (/ (count rs) 2) rs)]))
         (map (fn [[l r]]
                (cset/intersection (into #{} l) (into #{} r))))
         rs->priority
         (reduce +))))

(defn solve-2 []
  (let [rs (parse-input)]
    (->> rs
         (partition 3)
         (mapcat (fn [gr]
                   (->> gr
                        (map #(into #{} (string/split % #"")))
                        (apply cset/intersection)
                        rs->priority)))
         (reduce +))))
