; mocro

(define (3-state-attempt value 
         positive-body 
         zero-body 
         negative-body)
  (cond ((zero? value)     zero-body)
        ((positive? value) positive-body)
        (else              negative-body)))

(define-syntax 
  3-state
  (syntax-rules ()
      ((3-state
        value
        positive-body 
        zero-body 
        negative-body)
       (cond ((zero? value)     zero-body)
             ((positive? value) positive-body)
             (else              negative-body)))))
