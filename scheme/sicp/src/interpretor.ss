;;; interpretor
(load "~/bin/match.ss")

;;; calcalator
(define calc
  (lambda (exp)
    (match exp
      [,x (guard (number? x)) x]
      [(,op ,e1 ,e2)
       (let ([v1 (calc e1)]
             [v2 (calc e2)])
         (match op
           [+ (+ v1 v2)]
           [- (- v1 v2)]
           [* (* v1 v2)]
           [/ (/ v1 v2)]))])))

;;; lambda calculus
;;; variable : x
;;; function : (lambda (x) e)
;;; call : (e1 e2)

(define lookup
  (lambda (x scope)
    (let ([p (assq x scope)])
      (cond ((not p) x)
            (else
              (cdr p))))))

(define ext-scope
  (lambda (k v scope)
    (cons `(,k . ,v) scope)))

(define interp1
  (lambda (exp scope)
    (match exp
      [,x (guard (symbol? x)) (lookup x scope)]
      [,x (guard (number? x)) x]
      [(lambda (,x) ,e)
       `(closure ,exp ,scope)]
      [(,e1 ,e2)
       (let ([v1 (interp1 e1 scope)]
             [v2 (interp1 e2 scope)])
         (match v1
           [(closure (lambda (,x) ,e) ,scope)
            (interp1 e (ext-scope x v2 scope))]))]
      [(,op ,e1 ,e2)
        (let ([v1 (interp1 e1 scope)]
              [v2 (interp1 e2 scope)])
          (match op
            [+ (+ v1 v2)]
            [- (- v1 v2)]
            [* (* v1 v2)]
            [/ (/ v1 v2)]))])))

(define interp
  (lambda (exp)
    (interp1 exp '())))