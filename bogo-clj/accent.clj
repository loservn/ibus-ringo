(require 'clojure.string)

(def vowels
  ["aàáảãạ",
   "ăằắẳẵặ",
   "âầấẩẫậ",
   "eèéẻẽẹ",
   "êềếểễệ",
   "iìíỉĩị",
   "oòóỏõọ",
   "ôồốổỗộ",
   "ơờớởỡợ",
   "uùúủũụ",
   "ưừứửữự",
   "yỳýỷỹỵ"])

(defn contains-char? [the-string, the-char]
  (some #(= the-char %) the-string))

(defn get-vowel-family
  "Returns the family if the vowel is in one of the vowel families, nil
  otherwise."
  [chr]
  (first (filter #(contains-char? %1 chr) vowels)))

(defn add-accent-char
  "Adds an accent to a given char. Also remove any accent if the given accent is
  Accent.NONE"
  [chr accent]
  (get (get-vowel-family chr) accent chr))

(defn vowel?
  "doc-string"
  [chr]
  (some #(contains-char? %1 chr) vowels))


(defn separate
  "Separate a string into 3 parts - :head, :vowel and :last.
  Eg.
  > (separate \"chuyen\"
    {:last (n), :vowel (u y e), :head (c h)}"
  [string]
  (let
      [rstring (reverse string)
      [last-consonant stuff] (split-with (comp not vowel?) rstring)
      [vowel head] (split-with vowel? stuff)]
    (zipmap [:head :vowel :last] (map reverse [head vowel last-consonant]))))


(defn add-accent-string
  "doc-string"
  [string accent]
  (let [{:keys [head vowel] _last :last} (separate string)]
    (apply str (map #(apply str %)
      [head
      (case (count vowel)
        1 [(add-accent-char (first vowel) accent)]
        2 (if (= (last vowel) \y)
            [(add-accent-char (first vowel) accent) (last vowel)]
            [(first vowel) (add-accent-char (last vowel) accent)])
        3 (if (contains? [\o \i \u] (last vowel))
          [(first vowel) (add-accent-char (nth vowel 1) accent) (last vowel)]
          (concat (butlast vowel) [(add-accent-char (last vowel) accent)]))
        vowel)
      _last]))))

; (println (add-accent-char \a 3))
; (println (add-accent-char \ả 0))
; (println (add-accent-char \y 1))
; (println (vowel? \w))
; (println (vowel? \a))
(println (add-accent-string "chuyên" 2))
(println (add-accent-string "ây" 2))
(println (separate "chuyen"))
(println (add-accent-char \a 2))
