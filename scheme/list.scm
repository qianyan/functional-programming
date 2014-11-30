(define nums (list 1 3 2 4 1))

(define (sum list_of_values) 
  (if (= 1 (length list_of_values))
    (car list_of_values)
    (+ (car list_of_values) 
       (sum (cdr list_of_values)))))

; swap the order of second and third number
(define (swap-2-3 x)
  (list (car x)
        (caddr x)
        (cadr x)))

(define six_over_three '(/ 6 3)) 
(eval six_over_three) ;=> 2
(define switch (swap-2-3 six_over_three))
(eval switch) ;=>1/2

