;;gcd
(define (gcd a b) (if (= b 0)
	a
	(gcd b (remainder a b))))
(define (abs a)
  (cond ((>= 0 a) a)
        (else (- 0 a))))

;; excise 2.3
(define (make-rat n d)
  (cond ((< 0 (* n d)) (and (set! n (abs n)) (set! d (abs d))))
        ((< 0 n) (and (set! n (- 0 n)) (set! d (- 0 d)))))
  (let ((g (gcd n d)))
    (cons (/ n g) (/ d g))))

(define (number x) (car x))

(define (denom x) (cdr x))
;; rat data
(define (print-rat x)
	(newline)
	(display (number x))
	(display "/")
	(display (denom x)))

(define one-half (make-rat 1 2))

(print-rat one-half)

(define one-third (make-rat 1 3))

(print-rat one-third)

(define (mul-rat x y)
	(make-rat (* (number x) (number y))
                  (* (denom x) (denom y))))

(print-rat (mul-rat one-half one-third))
