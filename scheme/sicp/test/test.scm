(import
    (rough-draft unit-test)
    (rough-draft console-test-runner))

(load "../src/p96-symbol-data.scm")

;;; symbol data tests
(define-test-suite symbol-data
  (define-test should-make-elements-equal
    (assert-true (elements-equal '(1) '(1))))
  (define-test should-two-empty-list-are-equal
    (assert-true (elements-equal '() '())))
  (define-test should-not-diff-size-list-equal
    (assert-false (elements-equal '() '(1)))))

(run-test-suite symbol-data)
