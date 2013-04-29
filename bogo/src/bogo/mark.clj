(ns bogo.mark)

(def mark-affinity {
  :hat   {:family-a \â
          :family-o \ô
          :family-e \ê}
  :horn  {:family-o \ơ
          :family-u \ư}
  :breve {:family-a \ă}
  :bar   {:family-d \đ}})

(def mark-raw-affinity {
  :hat ["a" "o" "e"]
  :horn ["o" "u"]
  :breve ["a"]
  :bar ["d"]
  })

(defn get-family
  "doc-string"
  [chr]
  (case chr
    (\a \ă \â) :family-a
    (\e \ê)    :family-e
    (\o \ơ \ô) :family-o
    (\u \ư)    :family-u
    (\d \đ)    :family-d
    nil))

(defn add-mark-char
  "Add mark to a single char."
  [chr mark]
  (get (get mark-affinity mark) (get-family chr) chr))

(defn add-mark-string
  "doc-string"
  [string mark]
  ; find the last possible raw char that can go with the given mark in the string
  (let [idx (apply max
              (map #(.lastIndexOf string %) (mark-raw-affinity mark)))]
    (if (not= idx -1)
      (apply str
        (concat
          (take idx string)
          [(add-mark-char (get string idx) mark)]
          (nthrest string (inc idx))))
      string)))
