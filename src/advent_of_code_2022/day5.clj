(ns advent-of-code-2022.day5
  (:require [clojure.string :as string]))

(defn parse-input []
  (let [[crates moves] (-> "input/day5"
                           slurp
                           (string/split #"\n\n"))
        crates (string/split crates #"\n")
        crates (map (fn [l]
                      (map (fn [p]
                             (re-find #"[A-Z]" (apply str p)))
                           (partition-all 4 l)))
                    (drop-last 1 crates))]
    {:crates (into {}
                  (map-indexed (fn [i el]
                                 [(inc i)
                                  (reverse (into '() (filter some?
                                                            (map #(nth % el) crates))))])
                               (range (inc (count crates)))))
     :moves (map (fn [m]
                   (let [[_ & mvs] (string/split m #"move | from | to ")
                         [count from to] (map #(Integer/parseInt %) mvs)]
                     {:count count
                      :from from
                      :to to}))
                 (string/split moves #"\n"))}))

(defn execute-move [crates {:keys [count from to]}]
  (loop [crates crates count count]
    (if (= count 0)
      crates
      (recur (-> crates
                 (update to #(conj % (peek (crates from))))
                 (update from pop))
             (dec count)))))

(defn solve-1 []
  (let [{:keys [crates moves]} (parse-input)]
    (loop [crates crates [move & moves] moves]
      (if (nil? move)
        (apply str (->> crates
                        (map (fn [[k v]]
                               [k (peek v)]))
                        (sort-by first)
                        (map second)))
        (recur (execute-move crates move) moves)))))


(defn execute-move-2 [crates {:keys [count from to]}]
  (-> crates
      (update to #(concat (take count (crates from)) %))
      (update from #(drop count %))))

(defn solve-2 []
  (let [{:keys [crates moves]} (parse-input)]
    (loop [crates crates [move & moves] moves]
      (if (nil? move)
        (apply str (->> crates
                        (map (fn [[k v]]
                               [k (first v)]))
                        (sort-by first)
                        (map second)))
        (recur (execute-move-2 crates move) moves)))))
