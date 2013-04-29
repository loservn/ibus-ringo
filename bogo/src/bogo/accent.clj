(ns bogo.accent
  (:require [clojure.set]
            [bogo.util :refer :all]))

(def keyword-accent
  (zipmap [:none :grave :acute :hook :tilde :dot] (range 6)))

(def accent-keyword
  (clojure.set/map-invert keyword-accent))

(defn get-vowel-family
  "Returns the family if the vowel is in one of the vowel families, nil
  otherwise."
  [chr]
  (first (filter #(contains-char? %1 chr) vowels)))

(defn add-accent-char
  "Adds an accent to a given char. Also remove any accent if the given accent is
  Accent.NONE"
  [chr accent]
  (get (get-vowel-family chr) (keyword-accent accent) chr))

(defn add-accent-string
  "doc-string"
  [string accent]
  (let [{:keys [head vowel] _last :last} (separate string)]
    (apply str (map #(apply str %)
      [head
      (case (count vowel)
        1 [(add-accent-char (first vowel) accent)]
        2 (if (and (= (last vowel) \y) (not (some #{(first vowel)} [\o \u])))
            [(add-accent-char (first vowel) accent) (last vowel)]
            [(first vowel) (add-accent-char (last vowel) accent)])
        3 (if (some #{(last vowel)} [\o \i \u])
          [(first vowel) (add-accent-char (nth vowel 1) accent) (last vowel)]
          (concat (butlast vowel) [(add-accent-char (last vowel) accent)]))
        vowel)
      _last]))))

(defn get-accent-char
  "doc-string"
  [chr]
  (get accent-keyword
    (.lastIndexOf
      (or (get-vowel-family chr) "")
      (str chr)) :none))

(defn get-last-accent-string
  [string]
  (first (drop-while #{:none} (reverse (map get-accent-char string)))))
