var testCase  = require('nodeunit').testCase;
var _ = require('underscore');
var F = require('../src/ObjectOps');

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
    },
   "should all of true are true": function(test) {
       test.ok(F.allOf(true,true,true,true,true,true,true));
       test.done();
   },
   "should any of true are true": function(test) {
       test.ok(F.anyOf(true,true,false,true,true,true,true));
       test.done();
   },
   "should pair an object to k-v array": function(test) {
       var obj = {"001": "YES", "002": "NO", "003": "W"};
       var expected = [["001", "YES"], ["002", "NO"], ["003", "W"]];

       test.deepEqual(_.pairs(obj), expected);
       test.done();
   },
   "should wrap to an object": function(test) {
       var array = [["001", "YES"], ["002", "NO"], ["003", "W"]];
       var obj = {"001": "YES", "002": "NO", "003": "W"};

       test.deepEqual(_.object(array), obj);
       test.done();
   },
   "should select parts of an object": function(test) {
       var table = [{"001": "YES", "002": "NO", "003": "W"},{"001": "PP", "002": "OO", "003": "WW"}];
       var expectedTable = [{"001": "YES", "002": "NO"},{"001": "PP", "002": "OO"}];
       var result = F.select(table, ['001', '002']);

       test.deepEqual(result, expectedTable);
       test.done();
   },
   "should rename the key of an object": function(test) {
       var obj = {"001": "YES", "002": "NO", "003": "W"};
       
       test.deepEqual(F.rename(obj, {"001": "000"}), {"000": "YES", "002": "NO", "003": "W"});
       test.done();
   },
   "should batch rename an array: as": function(test) {
       var objs = [{"001": "YES"}, {"001": "NO"}, {"001": "W"}];

       test.deepEqual(F.as(objs, {"001": "000"}), [{"000": "YES"}, {"000": "NO"}, {"000": "W"}]);
       test.done();
   },
   "should select partial fields of table with conditions": function(test) {
       var people = [{"name": "ryan", "age": 20, "gender": 'male'}, 
       {"name": "james", "age": 30, "gender": 'male'},
       {"name": "derek", "age": 28, "gender": 'female'}];

       var result = [{"first name": "james", "gender": 'male', age: 30},
       {"first name": "derek","gender": 'female', age: 28}];


       test.deepEqual(F.restrict(F.select(F.as(people, {"name": 'first name'}), ['first name', 'gender', 'age']), function(person){
           return person.age > 25;
       }), result);

       test.done();
   }
});
