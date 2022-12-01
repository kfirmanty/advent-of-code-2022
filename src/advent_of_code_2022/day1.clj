(ns advent-of-code-2022.day1
  (:require [clojure.string :as string]))

(defn- parse-input []
  (map (fn [elf-input]
         (map #(Long/parseLong %) (string/split elf-input #"\n")))
       (-> "input/day1"
           slurp
           (string/split #"\n\n"))))

(defn solve-1a []
  (->> (parse-input)
       (map #(reduce + %))
       sort
       last))

(defn solve-1b []
  (->> (parse-input)
       (map #(reduce + %))
       sort
       reverse
       (take 3)
       (reduce +)))
