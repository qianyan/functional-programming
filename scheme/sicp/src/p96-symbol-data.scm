
;;; elements equals
(define (elements-equal l1 l2)
  (cond ((not (= (length l1) (length l2)))
         #f)
        ((and (null? l1) (null? l2))
         #t)
        ((and (eq? (car l1) (car l2)) (elements-equal (cdr l1) (cdr l2)))
         #t)
        (else
          #f)))

;;; deriv
(define (variable? x) (symbol? x))
(define (same-variables? v1 v2)
  (and (variable? v1) (variable? v2) (eq? v1 v2)))
(define (make-sum a1 a2) (list '+ a1 a2))
(define (make-product m1 m2) (list '* m1 m2))
(define (make-exponentiation u n) (list '^ u n))

(define (sum? x)
  (and (pair? x) (eq? (car x) '+)))
(define (addend s) (cadr s))
(define (augend s) (caddr s))
(define (product? x)
  (and (pair? x) (eq? (car x) '*)))
(define (multiplier p) (cadr p))
(define (multiplicand p) (caddr p))

(define (exponentiation? x)
  (and (pair? x) (eq? (car x) '^)))
(define (base x) (cadr x))
(define (exponent x) (caddr x))

(define (deriv exp var)
  (cond ((number? exp) 0)
        ((variable? exp)
         (if (same-variables? exp var) 1 0))
        ((sum? exp)
         (make-sum (deriv (addend exp) var)
           (deriv (augend exp) var)))
        ((product? exp)
         (make-sum
           (make-product (multiplier exp)
             (deriv (multiplicand exp) var))
           (make-product (deriv (multiplier exp) var)
             (multiplicand exp))))
        ((exponentiation? exp)
         (make-product
           (make-product (exponent exp)
             (make-exponentiation (base exp) (- (exponent exp) 1)))
           (deriv (base exp) var)))
        (else
          (error "unknown expression type -- DERIV" exp))))
