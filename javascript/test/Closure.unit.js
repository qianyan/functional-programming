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
    }

});
