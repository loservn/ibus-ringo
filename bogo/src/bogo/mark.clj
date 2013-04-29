(ns bogo.mark)

(def mark-afinity {
  :hat   {:family-a "â"
          :family-o "ô"
          :family-e "ê"}
  :horn  {:family-o "ơ"
          :family-u "ư"}
  :breve {:family-a "ă"}
  :bar   {:family-d "đ"}})

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
  (get (get mark-afinity mark) (get-family chr) chr))
