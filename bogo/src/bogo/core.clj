(ns bogo.core
  (:require [bogo.accent :refer :all]
            [bogo.mark :refer :all]))

(def simple-telex {
            "a" ["a^"]
            "o" ["o^"]
            "e" ["e^"]
            "w" ["u+" "o+" "a("]
            "d" ["dd"]
            "f" ["`"]
            "s" ["'"]
            "r" ["?"]
            "x" ["~"]
            "j" ["."]
        })

(defn in? [item collection]
  (some #{item} collection))

(defn interpret-viqr
  "doc-string"
  [string]
  (case (count string)
    1 {:action :add-accent
       :accent (case string
                  "`" :grave
                  "'" :acute
                  "?" :hook
                  "~" :tilde
                  "." :dot)}
    2 (if (in? (first string) [\a \o \e \u \d])
      {:action :add-mark
       :mark (case (last string)
                \^ :hat
                \+ :horn
                \( :breve
                \d :bar)
       :target (first string)}
      {:action nil})))

(defn execute-operation
  "doc-string"
  [string operation]
  (case (operation :action)
    :add-accent (add-accent-string string (operation :accent))
    :add-mark (add-mark-string string (operation :mark) (operation :target))))

(defn process-key
  "doc-string"
  [string chr]
  (reduce execute-operation string (map interpret-viqr (simple-telex chr))))

