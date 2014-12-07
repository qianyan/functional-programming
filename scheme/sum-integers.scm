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

;=> always equals itself
(define (identity x) x)

(define (sum-integers-alias a b)
  (sum identity a inc b))

;intergral
(define (intergral f a b dx)
  (define (add-dx x) (+ x dx))
  (* (sum f (+ a (/ dx 2.0)) add-dx b) dx))

