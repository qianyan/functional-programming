;;gcd
(define (gcd a b) (if (= b 0)
	a
	(gcd b (remainder a b))))

(define (make-rat n d)
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
