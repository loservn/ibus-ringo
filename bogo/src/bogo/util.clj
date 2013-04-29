(ns bogo.util)

(defmacro dbg[x] `(let [x# ~x] (println "dbg:" '~x "=" x#) x#))

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

(defn in? [item collection]
  (some #{item} collection))

(defn vowel?
  "doc-string"
  [chr]
  (some #(in? chr %1) vowels))

(defn word-boundary?
  [chr]
  (in? chr [\space \. \, \!]))

(defn separate
  "Separate a string into 3 parts - :head, :vowel and :last.
  Eg.
  > (separate \"chuyen\"
    {:last (n), :vowel (u y e), :head (c h)}"
  [string]
  (let
      [rstring (reverse string)
        [last-consonant stuff] (split-with (comp not vowel?) rstring)
        [vowel head] (let [[vowel- head-] (split-with vowel? stuff)]
                  (if (= (str (first head-) (last vowel-)) "qu")
                    [(butlast vowel-) (cons "u" head-)]
                    [vowel- head-]))]
    (zipmap [:head :vowel :last] (map reverse [head vowel last-consonant]))))
