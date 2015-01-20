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
    "should return default values for null": function(test) {
        var nums = [1, 2, 3, null, 5];
        var safeMulti = C.fnull(function(memo, n) {return memo * n}, 1);
        test.equal(_.reduce(nums, safeMulti), 30); 
        test.done();
    }
});
