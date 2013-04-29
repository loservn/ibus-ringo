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
  "Parses an atomic VIQR-like string and returns a map indicating the necessary
  operation and arguments going with it."
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
  "Execute the operation parsed by `interpret-viqr` on the given string."
  [string operation]
  (case (operation :action)
    :add-accent (add-accent-string string (operation :accent))
    :add-mark (add-accent-string
      (add-mark-string
        (add-accent-string string :none)
        (operation :mark)
        (operation :target))
      (get-last-accent-string string))
    :append-char (str string (operation :char))))

(defn get-transformation-list
  "Finds the list of possible transformations written in a VIQR-like syntax that
  a keypress can generate. Returns an empty list if no transformation is defined
  for that key."
  [chr input-method-map]
  (get input-method-map chr []))

(defn process-key
  "Process a single keypress."
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
  "Process a key sequence."
  [key-sequence]
  (reduce process-key "" (seq key-sequence)))
