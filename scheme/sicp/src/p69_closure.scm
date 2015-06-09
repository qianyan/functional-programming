;;; remove atom from a list
(define (rember a l)
  (cond ((null? l) '())
        ((eq? a (car l)) (cdr l))
        (else (cons (car l)
                    (rember a (cdr l))))))
;;; first of all pair
(define (firsts l)
  (if (null? l) '()
      (cons (car (if (pair? (car l))
                     (car l)
                     (list (car l))))
            (firsts (cdr l)))))

;; last pair
(define (last-pair l)
  (cond ((null? l) '())
        ((null? (cdr l)) (car l))
        (else (last-pair (cdr l)))))

(define (last-pair1 l)
  (let ((rest (cdr l)))
    (if (null? rest)
        (car l)
        (last-pair1 rest))))

;; reverse
(define (reverse elems)
  (if (null? elems)
      '()
      (append (reverse (cdr elems)) (list (car elems)))))

;; map
(define (map proc items)
  (if (null? items)
      '()
      (cons (proc (car items)) (map proc (cdr items)))))

(define (square-list items)
  (define (iter things answer)
    (if (null? things)
        answer
        (iter (cdr things)
              (append answer (list ((lambda (x) (* x x)) (car things)))))))
  (iter items '()))

;;; length
(define (length items)
  (if (null? items)
      0
      (+ (length (cdr items) 1))))

(define (lat? l)
  (cond ((null? l) #t)
        ((atom? (car l)) (lat? (cdr l)))
        (else #f)))

(define us-coins (list 50 25 10 5 1))
(define uk-coins (list 100 50 20 10 5 2 1 0.5))
;;; coins exchanges
(define (cc amount coin-values)
  (cond ((= amount 0) 1)
        ((or (< amount 0) (null? coin-values)) 0)
        (else
         (+ (cc amount (cdr coin-values))
            (cc (- amount
                   (car coin-values))
                coin-values)))))

;;; varargs
(define (f x y . z) (display z))


;;; same parity
(define (same-parity x . l)
  (if (null? l)
      (cons x '())
      (cons x (filter (lambda (a) (= (mod x 2) (mod a 2))) l))))
;;; same parity 2
;;; same-value-after-mod2
(define (same-value-after-mod2 x y)
  (= (mod x 2) (mod y 2)))
(define (same-parity-2 x . l)
  (define (same-parity-recurisive a bs)
      (cond ((null? bs)
             (cons a '()))
            ((same-value-after-mod2 x a)
             (cons a (same-parity-recurisive (car bs) (cdr bs))))
            (else
              (same-parity-recurisive (car bs) (cdr bs)))))
  (same-parity-recurisive x l))

(define (foreach proc l)
  (if (not (null? l))
      (foreach ((lambda (x) proc) (proc (car l))) (cdr l))))

(define (for-each-1 proc l)
  (define (recur a ls)
    (if (null? ls)
        #t
        (recur (proc (car ls)) (cdr ls))))
  (recur 'everthing l))

(define (for-each proc l)
  (if (null? l)
      #t
      (for-each ((lambda (x) proc) (proc (car l))) (cdr l))))

