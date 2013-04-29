(ns bogo.mark-test
  (:require [clojure.test :refer :all]
            [bogo.mark :refer :all]))

(deftest test-add-mark-char
  (testing "Simple char."
    (is (= (add-mark-char \a :hat) \â)))
  (testing "Impossible combinations."
    (is (= (add-mark-char \a :horn) \a))
    (is (= (add-mark-char \w :horn) \w))))

(deftest test-add-mark-string
  (testing "Single char."
    (is (= (add-mark-string "a" :hat) "â")))
  (testing "Long string."
    (is (= (add-mark-string "trang" :breve) "trăng"))
    (is (= (add-mark-string "dêm" :bar) "đêm")))
  (testing "Impossible combinations."
    (is (= (add-mark-string "a" :horn) "a"))))
