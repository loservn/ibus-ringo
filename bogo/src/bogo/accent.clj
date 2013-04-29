(ns bogo.accent)

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

(defn- convert-accent-keyword [kw]
  ((zipmap [:none :grave :acute :hook :tilde :dot] (range 6)) kw))

(defn add-accent-char
  "Adds an accent to a given char. Also remove any accent if the given accent is
  Accent.NONE"
  [chr accent]
  (get (get-vowel-family chr) (convert-accent-keyword accent) chr))

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
        2 (if (and (= (last vowel) \y) (not (some #{(first vowel)} [\o \u])))
            [(add-accent-char (first vowel) accent) (last vowel)]
            [(first vowel) (add-accent-char (last vowel) accent)])
        3 (if (some #{(last vowel)} [\o \i \u])
          [(first vowel) (add-accent-char (nth vowel 1) accent) (last vowel)]
          (concat (butlast vowel) [(add-accent-char (last vowel) accent)]))
        vowel)
      _last]))))

