var testCase  = require('nodeunit').testCase;
var C = require('../src/Closure');

module.exports = testCase({
    "should pluck field from object": function(test) {
        var obj = {name: "ryan"};
        test.equal(C.pluck('name')(obj), 'ryan');
        test.done();
    }
});
