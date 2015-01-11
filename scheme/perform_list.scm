;; get nth element from list
(define (list-ref items n)
  (if (= n 0)
      (car items)
      (list-ref (cdr items) (- n 1))))

(define squares (list 1 4 9 16 25))

(display (list-ref squares 3))


(define (length items)
  (if (null? items)
      0
      (+ 1 (length (cdr items)))))

(define odds (list 1 3 5 7))

(newline)
(display (length odds))

;; non-recursive length

(define (length2 items)
  (define (length-with-count items count)
    (if (null? items)
        count
        (length-with-count (cdr items) (+ 1 count))))
  (length-with-count items 0))

(newline)
(display (length2 odds))
