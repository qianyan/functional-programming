var testCase  = require('nodeunit').testCase;
var _ = require('underscore');

module.exports = testCase({

    "should map to new object": function (test) {
        var obj = {"001": "YES", "002": "NO", "003": "W"};
        var expectedObj = [ {code: 001, answer: "YES"}, {code: 002, answer: "NO"}, {code: 003, answer: "W"} ];
        var actualObj = _.map(obj, function(v, k) {
            return {code: k, answer: v};
        });
        test.deepEqual(actualObj, expectedObj);
        test.done();
    },

    "should get a values array for an object": function (test) {
        var obj = {"001": "YES", "002": "NO", "003": "W"};
        var expectedArray = ['YES', 'NO', 'W'];
        var actualArray = _.map(obj, _.identity);

        test.deepEqual(actualArray, expectedArray);
        test.done();
    },

    "should reduce an array to an object": function (test) {
        var array = ['YES', 'NO', 'W'];

        var actual = _.reduce(_.map(array, function(v, i) {
            var o = {};
            o[ "00" + (i+1) ] = v;
            return o;
        }) , function(memo, next) { return _.extend(memo, next); }, {});
        var expected = {"001": "YES", "002": "NO", "003": "W"};

        test.deepEqual(actual, expected);
        test.done();
    },
    "should reduce right an array to an object": function (test) {
        var array = ['YES', 'NO', 'W'];

        var actual = _.reduceRight(_.map(array, function(v, i) {
            var o = {};
            o[ "00" + (i+1) ] = v;
            return o;
        }) , function(memo, next) { return _.extend(memo, next); }, {});
        var expected = {"001": "YES", "002": "NO", "003": "W"};

        test.deepEqual(actual, expected);
        test.done();
    } 
})
