(import (rough-draft unit-test)
  (rough-draft console-test-runner))


(load "../src/p69_closure.scm")
(load "../src/p96-symbol-data.scm")

(define-test-suite how-to-use-list
  (define-test should-remove-atom-from-list
    (assert-equal? (rember 'apple '(apple (apple) pear strawberry))
      '((apple) pear strawberry)))
  (define-test should-not-remove-from-empty-list
    (assert-equal? (rember 'apple '())
      '()))
  
  (define-test should-get-last-pair-of-list
    (assert-equal? (last-pair '(apple pear banana)) 'banana)
    (assert-equal? (last-pair '()) '()))
  
  (define-test should-reverse-all-of-elems
    (assert-equal? (reverse '(a b c)) '(c b a))
    (assert-equal? (reverse '()) '())
    (assert-equal? (reverse '(a b '(c))) '('(c) b a))
    (assert-equal? (reverse '(a)) '(a))))

 ;;; deriv tests
(define-test-suite deriv-suite
  (define-test should-follow-deriv-rule
    (assert-equal? (deriv '(+ x 3) 'x)
      '(+ 1 0))
    (assert-equal? (deriv '(* x y) 'x)
      '(+ (* x 0) (* 1 y)))
    (assert-equal? (deriv '(^ x 3) 'x)
      '(* (* 3 (^ x 2)) 1))))

(run-test-suites how-to-use-list deriv-suite)

