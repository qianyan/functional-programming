(define  (sum-integers a b) 
  (if  (> a b) 
    0
    (+ a  (sum-integers  (+ a 1) b))))


(define (sum term a next b)
  (if (> a b)
    0
    (+ (term a) 
       (sum term (next a) next b))))

(define (inc n) (+ n 1))

(define (cube n) (* n n n))

(define (sum-cubes a b) 
  (sum cube a inc b))
