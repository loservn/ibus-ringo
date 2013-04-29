(ns bogo.util)

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

(defn vowel?
  "doc-string"
  [chr]
  (some #(contains-char? %1 chr) vowels))

(defn in? [item collection]
  (some #{item} collection))

(defn word-boundary?
  [chr]
  )

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


