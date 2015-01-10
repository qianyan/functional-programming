(define new-withdraw
  (let ((balance 100))
    (lambda (amount)
      (if (>= balance amount)
      (begin (set! balance (- balance amount))
             balance)
      "Insufficient funds"))))
