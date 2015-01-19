var testCase  = require('nodeunit').testCase;
var C = require('../src/Closure');

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
    }
});
