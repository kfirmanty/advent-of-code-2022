(ns advent-of-code-2022.day2
  (:require [clojure.string :as string]))

(defn- parse-input []
  (map (fn [round-input]
         (string/split round-input #"\s+"))
       (-> "input/day2"
           slurp
           (string/split #"\n"))))

(defn ->outcome [[m1 m2 :as m]]
  (cond (= m1 m2) :draw
        (#{[:rock :paper] [:scissors :rock] [:paper :scissors]} m) :win
        :else :loose))

(defn play-game-1 [{:keys [strategy scoring points]} m]
  (let [[m1 m2 :as m] (map strategy m)]
    (+ (scoring (->outcome m))
       (points m2))))

(defn solve-1a []
  (let [rules {:strategy {"X" :rock
                          "Y" :paper
                          "Z" :scissors
                          "A" :rock
                          "B" :paper
                          "C" :scissors}
               :points {:rock 1
                        :paper 2
                        :scissors 3}
               :scoring {:loose 0
                         :draw 3
                         :win 6}}]
    (->> (parse-input)
         (map #(play-game-1 rules %))
         (reduce +))))

(defn play-game-2 [{:keys [strategy scoring points]} [m1 m2 :as m]]
  (let [m1 ((strategy m1))
        m2 ((strategy m2) m1)]
    (+ (scoring (->outcome [m1 m2]))
       (points m2))))

(defn- rmap [m]
  (->> m
       (map (fn [[k v]]
              [v k]))
       (into {})))

(def loosing-map {:rock :scissors
                  :paper :rock
                  :scissors :paper})
(defn solve-2 []
  (let [rules {:strategy {"X" (fn [m] (get loosing-map m))
                          "Y"  identity
                          "Z" (fn [m] (get (rmap loosing-map) m))
                          "A" (fn [& _] :rock)
                          "B" (fn [& _] :paper)
                          "C" (fn [& _] :scissors)}
               :points {:rock 1
                        :paper 2
                        :scissors 3}
               :scoring {:loose 0
                         :draw 3
                         :win 6}}]
    (->> (parse-input)
         (map #(play-game-2 rules %))
         (reduce +))))
