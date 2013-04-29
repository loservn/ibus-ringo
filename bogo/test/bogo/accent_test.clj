(ns bogo.accent-test
  (:require [clojure.test :refer :all]
            [bogo.accent :refer :all]))

(deftest test-add-accent-string
  (testing "Single-char vowel."
    (is (= (add-accent-string "a" :acute)) "á")
    (is (= (add-accent-string "nhan" :acute)) "nhán")
    (is (= (add-accent-string "ngang" :acute)) "ngáng"))
  (testing "Double-char vowel."
    (is (= (add-accent-string "hoa" :grave) "hoà"))
    (is (= (add-accent-string "quy" :grave) "quỳ"))
    (is (= (add-accent-string "mươn" :dot) "mượn"))
    (is (= (add-accent-string "thuôm" :dot) "thuộm")))
  (testing "Triple-char vowel."
    (is (= (add-accent-string "thiêu" :acute) "thiếu"))
    (is (= (add-accent-string "thuyên" :grave) "thuyền"))))

(deftest test-get-accent-char
  (testing "Simple accent"
    (is (= (get-accent-char \ả) :hook))
  (testing "No accent"
    (is (= (get-accent-char \a) :none))
    (is (= (get-accent-char \e) :none)))
  (testing "Consonants"
    (is (= (get-accent-char \w) :none))
    (is (= (get-accent-char \n) :none)))))
