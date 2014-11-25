(define (sqr x) (* x x))
(define (cube x) (* (sqr x) x))

(define (apply_twice fn value) (fn (fn value)))

(define (four_power x) (apply_twice sqr x))
