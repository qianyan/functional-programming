(ns foo.core)

(defn Y [x]
  ((fn [n] x) (println x)))

(defn parse-args [args]
  (into {} (map (fn [[k v]] [(keyword (.replace  k "--" "")) v])
                (partition 2 args))))


(defn print-hello-world-times-of [n]
  (if (= n 0) nil
      (((fn [x] print-hello-world-times-of) (println "Hello World")) (- n 1))))

;;; ((fn [n] (dotimes [x n] (println "Hello World"))) 3)

((fn [num lst] (for [x lst] (dotimes [n num] (println x)))) 3 [1, 2, 3])

;;; filter
(fn [delim ls]
  (loop [result [] lst ls]
    (if (empty? lst) result
        (recur (if (> delim (first lst))
                 (conj result (first lst))
                 result)
               (rest lst)))))

;;; filter function
(fn [delim ls]
  (loop [a 0 lst ls]
    (cond
      (empty? lst) nil
      :else (recur (if (> delim (first lst)) (println (first lst))) (rest lst)))))

;;; odd postion
(fn [ls]
  (loop [odd true lst ls result []]
    (if (empty? lst) result
        (if (true? odd)
          (recur false (rest lst) result)
          (recur true (rest lst) (conj result (first lst)))))))

(fn [x]
  (loop [n x result []]
    (if (zero? n) result
        (recur (- n 1) (cons n result)))))

;;; reverse list
(fn [ls]
  (loop [lst ls result []]
    (if (empty? lst) result
        (recur (rest lst) (cons (first lst) result)))))

(defn reverse [ls]
  (if (empty? ls) []
      (conj (#'foo.core/reverse (rest ls)) (first ls))))


(defn sum [ls]
  (cond
    (empty? ls) 0
    (odd? (first ls)) (+ (first ls) (sum (rest ls)))
    :else (sum (rest ls))))

(defn length [lst]
  (if (empty? lst) 0
      (+ 1 (length (rest lst)))))

(fn [lst]
  (map #(Math/abs %) lst))

(defn s [x]
  (loop [sum 1 prev 1 i 1]
    (if (> i 9)
      (format "%.4f" sum)
      (let [cur (/ (* prev x) i)]
        (recur (+ sum cur) cur (+ i 1))))))

;;; e^x
(fn [n lst]
  (map (fn [x]
         (loop [sum 1 prev 1 i 1]
           (if (> i 9) (format "%.4f" sum)
               (let [cur (/ (* prev x) i)]
                 (recur (+ sum cur) cur (+ i 1)))))) lst))

;;; GCD
(defn gcd [x y]
  (loop [m x n y]
    (if (zero? n) m
        (recur n (mod m n)))))

;;; fabonacci sequence
(defn fabo [n]
  (loop [pre 0 cur 1 counter 1]
    (if (= counter n) pre
        (recur cur (+ pre cur) (+ 1 counter)))))

;;; pascal's triangle, but ending with nil.
(defn pascal-triangle [k]
  (let [X (fn [n] (reduce * (range 1 (inc n))))]
    (dotimes [r k]
      (dotimes [c (inc r)]
        (print (format (if (zero? c) "%s" " %s") (/ (X r) (* (X c) (X (- r c)))))))
      (println))))

;;(let [f  [m] (map read-string (re-seq #"\d+" (read-line)))] (println (f m)))
;;(let [f  m  (read-string (read-line))] (println (f m)))
;;(let [f  [m n] (line-seq (java.io.BufferedReader. *in*))] (println (f m)))

(defn string-mingle [p q]
  (apply str (map str (seq p) (seq q))))

;;; string-o-premute
(defn string-o-premute [s]
  (let [l (.length s ) m (range 0 l 2) n (range 1 l 2)]
    (apply str (map str (map (fn [n] (nth s n)) n) (map (fn [n] (nth s n)) m)))))

(defn string-compression [s]
  (apply str (map (fn [v]
                    (str (v 1) (let [l  (.length (v 0))]
                                 (if (= 1 l) ""
                                     l)))) (re-seq #"(.)\1*" s))))

(defn string-reduction [s]
  (apply str (apply sorted-set-by (fn [x y] (< (.indexOf s (str x)) (.indexOf s (str y)))) s)))

(defn filter-elements [count, arr]
  ((fn [s] (if (empty? s) -1
               s)) (map first
                  (filter (fn [m] (>= (second m) count)) (frequencies arr)))))

(defn super-digit [n k]
  (loop [s (* (reduce + (map read-string (clojure.string/split (str n) #""))) k)]
    (if (< s 10) s
        (recur (reduce + (map read-string (clojure.string/split (str s) #"")))))))
