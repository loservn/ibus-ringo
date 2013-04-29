(ns bogo.core
  (:require [bogo.accent :refer :all]
            [bogo.mark :refer :all]
            [bogo.util :refer :all]))

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
      (case (first string)
        \+ {:action :append-char :char (last string)}
        {:action nil}))))

(defn execute-operation
  "doc-string"
  [string operation]
  (case (operation :action)
    :add-accent (add-accent-string string (operation :accent))
    :add-mark (add-mark-string string (operation :mark) (operation :target))
    :append-char (str string (operation :char))))

(defn get-transformation-list
  "doc-string"
  [chr input-method-map]
  (get input-method-map chr [(str "+" chr)]))

(defn process-key
  "doc-string"
  [string chr]
  (let [result (reduce
      execute-operation
      string
      (map
        interpret-viqr
        (get-transformation-list (str chr) simple-telex)))]
    (if (= result string)
      (execute-operation string (interpret-viqr (str "+" chr)))
      result)))

(defn process-seq
  [key-sequence]
  (reduce process-key "" (seq key-sequence)))
