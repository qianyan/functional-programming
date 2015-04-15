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
