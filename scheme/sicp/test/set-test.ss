(import (rough-draft unit-test)
  (rough-draft console-test-runner))

(load "../src/p103_set.ss")

(define-test-suite set-operation
  (define-test should-find-element-of-set
    (assert-true (element-of-set 1 '(1 2 3)))))

(run-test-suite set-operation)

