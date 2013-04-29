(ns bogo.core-test
  (:require [clojure.test :refer :all]
            [bogo.core :refer :all]))

(deftest test-process-seq
  (testing "FIXME, I fail."
    (is (= (process-seq "there") "thể"))
    (is (= (process-seq "theer") "thể"))))
