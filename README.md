functional-programming
======================

functional programming based on various language.

## Java 
- [x] foldLeft, reduce
- [x] recursive data structure

## scheme
### S-Expression definition
1. an atom, or
2. an expression of the form (x • y) where x and y are s-expressions.

### Lisp is homoiconic language.
>
If a language has homoiconicity, it means that the language text has the same structure as its abstract syntax tree (i.e. the AST and the syntax are isomorphic)

### Cons
1. Cons is the fundamental function in most dialects of Lisp programming language. cons **cons**truct memory objects which hold two values or poiners to values.
2. Cons looks like
```
(cons 42 (cons 69 (cons 613 nil)))
```
![Cons-cells](resources/Cons-cells.png)

### Non-atomic S-expressions
#### history
>
Lisp was originally implemented on the IBM 704 computer, in the late 1950s. The 704 hardware had  special support for splitting a 36bits machine word into four parts. and "address part" and "decrement part" of 15bits each and "prefix part" and "tag part" of three bits each.

```
car (short for "Contents of the Address part of Register number"),
cdr ("Contents of the Decrement part of Register number"),
cpr ("Contents of the Prefix part of Register number"), and
ctr ("Contents of the Tag part of Register number"),
```

### example
1. sum(a, b) => a + sum(a+1, b)

```
(+ a (f (+ a 1) b)) 
```

2. definition of data structure

* list
```
(list 1 2 3)
'(1 2 3)
(quote (1 2 3))
```
* macro
```
(define-syntax 
  (syntax-rules () ...))
```

## Javascript 
### Javascript closure
```
var name = "global";

function varClosestPriciple(name) {
    return function(name) {
        console.log(name);
    };  
}
var p = varClosestPriciple();
p("hello"); 
=> hello
p()
=> undefined

var p1 = varClosestPriciple("world");
p("hello"); 
=> hello
p(); 
=> undefined
```
