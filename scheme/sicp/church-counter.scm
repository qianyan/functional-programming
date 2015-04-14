(define zero
  (lambda (f) (lambda (x) x)))

(define (add-1 n) 
  (lambda (f) (lambda (x) (f ((n f) x)))))

(define one
  (add-1 zero))

(define (add m n)
  (lambda (f) (lambda (x) ((m f) ((n f) x)))))
