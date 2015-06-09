(define (element-of-set e s)
  (cond ((null? s) #f)
        ((eq? (car s) e) #t)
        (else
          (element-of-set e (cdr s)))))
