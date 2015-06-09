(define draw-asterisk
  (lambda (n)
    (display '*)))

(define draw-whitespace
  (lambda (n)
    (display " ")))
(define (draw-horizontal n proc)
  (proc n)
  (if (= n 1) (newline)
      (draw-horizontal (- n 1) proc)))

(define (draw-vertical n proc)
  (proc n)
  (newline)
  (if (not (= n 1)) (draw-vertical (- n 1) proc)))

;;; draw a horizontal line
(define (draw-a-horizontal-line n)
  (draw-horizontal n draw-asterisk))

;;; draw a vertical line
(define (draw-a-vertical-line n)
  (draw-vertical n draw-asterisk))

;;; Draw a right triangle 
(define (draw-horizontal-without-newline n proc)
  (proc n)
  (if (not (= n 1)) (draw-horizontal-without-newline (- n 1 ) proc)))


(define draw-a-right-triangle
  (lambda (n)
    (draw-vertical n (lambda (m)
                       (draw-horizontal-without-newline (+ (- n m) 1) draw-asterisk)))))

;;; Diamond Exercises
;;; draw-isosceles-triangle
(define draw-isosceles-triangle
  (lambda (n)
    (if (zero? n) (newline)
        (draw-vertical n (lambda (m)
                           (draw-horizontal-without-newline (- (* 2 n) 1) (lambda (c)
                                                                            (if (< (abs (- c n)) (+ 1 (- n m)))
                                                                                                     (draw-asterisk '())
                                                                                                     (draw-whitespace '())))))))))
;;; draw diamond
(define draw-a-diamond
  (lambda (n)
    (if (zero? n) (newline)
        (draw-vertical (- (* 2 n) 1) (lambda (m)
                                       (draw-horizontal-without-newline (- (* 2 n) 1) (lambda (c)
                                                                                        (if (< (abs (- c n)) (- n (abs (- m n))))
                                                                                            (draw-asterisk '())
                                                                                            (draw-whitespace '())))))))))
;;; draw-diamond-with-your-name
(define draw-diamond-with-your-name
  (lambda (n)
    (if (zero? n) (newline)
        (draw-vertical (- (* 2 n) 1) (lambda (m)
                                       (if (= m n) (display "fangzhigang")
                                           (draw-horizontal-without-newline (- (* 2 n) 1) (lambda (c)
                                                                                            (if (< (abs (- c n)) (- n (abs (- m n))))
                                                                                                (draw-asterisk '())
                                                                                                (draw-whitespace '()))))))))))

;;; FizzBuzz
(define (fb s e)
  (if (> s e) (newline)
      (draw-vertical (- e s)
        (lambda (m)
          (let ((row (+ s (- e s m))))
            (cond ((and (zero? (mod row 3)) (zero? (mod row 5))) (display "FizzBuzz"))
                  ((zero? (mod row 3)) (display "Fizz"))
                  ((zero? (mod row 5)) (display "Buzz"))
                  (else
                    (display row))))))))

(define FizzBuzz
  (lambda ()
    (fb 1 100)))

;;; prime factor
(define (is? i n)
  (cond ((> i (sqrt n)) #t)
        ((zero? (mod n i)) #f)
        (else
          (is? (+ i 1) n))))

(define isPrime?
  (lambda (n)
    (is? 2 n)))

(define (divable-list i n)
  (cond ((> i n) '())
        ((zero? (mod n i)) (cons i (divable-list (+ i 1) n)))
        (else
          (divable-list (+ i 1) n))))

(define (generate n)
  (filter isPrime? (divable-list 2 n)))
