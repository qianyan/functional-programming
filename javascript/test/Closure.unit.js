var testCase  = require('nodeunit').testCase;
var C = require('../src/Closure');
var _ = require('underscore');

module.exports = testCase({
    "should pluck field from object": function(test) {
        var obj = {name: "ryan"};
        test.equal(C.pluck('name')(obj), 'ryan');
        test.done();
    },
    "should get object with a private field": function(test) {
        test.equal(C.O.inc(1), 1);
        test.equal(C.O.inc(2), 3);
        test.equal(C.O.inc(1), 4);
        test.done();
    },
    "should make unique string":function(test) {
        var func = C.mkUniqueString(0);
        test.equal(func("prefix"), "prefix0");
        test.equal(func("prefix"), "prefix1");
        test.equal(func("prefix"), "prefix2");
        test.done();
    },
    "should replace null with default values": function(test) {
        var nums = [1, 2, 3, null, undefined, 5];
        var safeMulti = C.fnull(function(memo, n) {return memo * n}, 1);
        test.equal(_.reduce(nums, safeMulti), 30); 
        test.done();
    },
    "should check and return message if fail": function(test) {
        var check = C.check(C.by(function(a){ return !_.isNull(a) && !_.isUndefined(a)},
                    'cannot be null or undefined'));
        test.deepEqual(check(null), ['cannot be null or undefined']); 
        test.deepEqual(check({}), []); 
        test.done();
    },
    "should check has keys for an object": function(test) {
        function hasKeys(){
            var args = _.toArray(arguments);
            return function(obj){
                return _.every(args, function(key){
                    return _.has(obj, key);
                }) }}

        var check = C.check(C.by(function(a){ return !_.isNull(a) && !_.isUndefined(a)}, 'cannot be null or undefined'),
                C.by(hasKeys('a', 'b'), 'Must have values for keys: a b'));

        test.deepEqual(check({}), ['Must have values for keys: a b']); 
        test.deepEqual(check({a: 'a', b: 'b'}), []); 
        test.deepEqual(check(null), ['cannot be null or undefined', 'Must have values for keys: a b']); 
        test.done();
    },
    "should invoke method": function(test) {
        var rev =  C.invoker('reverse', Array.prototype.reverse);
        var result = rev([1, 2, 3])
        test.deepEqual(result, [3, 2, 1]);
        test.done();
    },
    "should dispatch different methods": function(test) {
        var str = C.dispatch(C.invoker('toString', String.prototype.toString),
                C.invoker('toString', Array.prototype.toString));

        test.equal(str('abc'), 'abc');
        test.equal(str([1, 2, 3]), '1,2,3');
        test.done();
    },
    "should curry left": function(test) {
        var divide10By = C.leftCurryDiv(10);
        var divideBy10 = C.rightCurryDiv(10);

        test.equal(divide10By(5), 2);
        test.equal(divideBy10(20), 2);
        test.done();
    }, 
    "curry": function(test) {
        test.notDeepEqual(['11','11','11','11'].map(parseInt), [111, 11, 11, 11]);
        test.deepEqual(['11','11','11','11'].map(C.curry(parseInt)), [11, 11, 11, 11]);
        test.done();
    },
    "curry2 with function parameters": function(test) {
        var peopleCount = C.curry2(_.countBy)(function nameWithGender(person){
            return [person.name, person.gender].join('-');
        });

        var people = [{name: 'A', gender: 'male'}, {name: 'B', gender: 'male'},{name: 'A', gender: 'male'},{name: 'A', gender: 'female'}];

        var expected = {'A-male': 2,
            'B-male': 1,
            'A-female': 1
        };
        var result = peopleCount(people);
        test.deepEqual(result, expected);
        test.done();
    },
    "partial - preconditions": function(test) {
        var zero = C.by(function(n) {return 0 === n}, 'cannot be zero');

        function sqr(n)  {
            if(zero(n)) throw new Error(zero.message);

            return n * n;
        }

        test.equal(sqr(2), 4);
        test.throws(function(){sqr(0)}, 'cannot be zero');
        test.done();
    },
    "partial - compose": function(test) {
        var not = function(x) {return !x;}
        test.ok(_.compose(not, _.isString)([]));
        test.done();
    },
    "partial - compose mapcat": function(test) {
        var irregularArr = [[1,2], [3], [0, 4, 5]];
        function cat() {
            var array = _.first(arguments);
            var head = _.first(array);
            return head.concat.apply(head, _.rest(array));
        }

        var magicFunc = _.compose(cat, _.map);
        test.deepEqual(magicFunc(irregularArr, _.identity), [1,2,3,0,4,5]);
        test.done();
    },
    "recursion - cycle": function(test) {
        var result = C.cycle(3, [1, 2]);
        test.deepEqual(result, [1, 2, 1, 2, 1, 2]);
        test.done();
    },
    "unzip": function(test) {
        var result = C.unzip(_.zip([1, 2], ['a', 'b']));
        test.deepEqual(result, [[1, 2], ['a', 'b']]);
        test.done();
    },
    "flat": function(test){
        var result = C.flat([[1,2,3], [4,5],[[6,7]]]);
        test.deepEqual(result, [1,2,3,4,5,6,7]);
        test.done();
    },
    "deep clone": function(test) {
        var x = {a: {b: {d: []}}};
        var y = C.deepClone(x);
        y.a.b.d = {};
        test.ok(!_.isEqual(x,y));
        test.done();
    },
    "visit in an array": function(test) {
        test.deepEqual(C.visit(_.isNumber, _.identity, [1, null, 2]), [true, false, true]);
        test.done();
    },
    "generator": function(test) {
        var ints = C.generator(0, _.identity, function(n) {return n+1});
        test.equal(ints.head, 0);
        test.equal(ints.tail().head, 1);
        test.done();
    },
   "partial - random": function(test) {
       var rand = C.partial(_.random, 1);
       test.ok(rand(10) <= 10 && rand(10) > 0)
       test.done();
   }
});
