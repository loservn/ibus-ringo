(ns bogo.accent-test
  (:require [clojure.test :refer :all]
            [bogo.accent :refer :all]))

(deftest test-add-accent-string
  (testing "Single-char vowel."
    (is (= (add-accent-string "a" 2)) "á")
    (is (= (add-accent-string "nhan" 2)) "nhán")
    (is (= (add-accent-string "ngang" 2)) "ngáng"))
  (testing "Double-char vowel."
    (is (= (add-accent-string "hoa" 1) "hoà"))
    (is (= (add-accent-string "quy" 1) "quỳ"))
    (is (= (add-accent-string "mươn" 5) "mượn"))
    (is (= (add-accent-string "thuôm" 5) "thuộm")))
  (testing "Triple-char vowel."
    (is (= (add-accent-string "thiêu" 2) "thiếu"))
    (is (= (add-accent-string "thuyên" 1) "thuyền"))))
