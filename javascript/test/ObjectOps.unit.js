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
    "should get values from an object": function (test) {
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
    "should reduce an array to an object - 1": function (test) {
        var array = ['YES', 'NO', 'W'];
        var actual = _.object(['001', '002', '003'], array);
        var expected = {"001": "YES", "002": "NO", "003": "W"};

        test.deepEqual(actual, expected);
        test.done();
    },
    "should reduce an array to an object - 2": function (test) {
        var array = ['YES', 'NO', 'W'];
        var actual = _.object(_.map(array, function(v, i){
            return ['00' + (i+1), v];
        }));
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
   "should batch rename an array": function(test) {
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
   },
   "should generate value many times": function(test) {
       test.deepEqual(F.repeat(3, 'hello'), ['hello', 'hello', 'hello']);
       test.done();
   },
   "should repeatly generate value many times": function(test) {
       test.deepEqual(F.repeatedly(3, function() {return 'hello'}), ['hello', 'hello', 'hello']);
       test.done();
   },
   "should reverse string": function(test) {
       test.equal(F.stringReverse('abc'), 'cba');
       test.equal(F.stringReverse('a'), 'a');
       test.equal(F.stringReverse(1), undefined);
       test.done();
   },
   "should do when expr is true": function(test) {
       test.ok(F.doWhen(true, function(){return true}))
       test.ok(F.doWhen(0 < 1, function(){return true}))
       test.ok(F.doWhen(true, function(){return true}) || F.doWhen(0 < 1, function(){return true}));
       test.done();
   },
   "should sort an array with lazyChain": function(test) {
       var toSort = new F.lazyChain([1, 20, 3]).invoke('sort', function(a, b){return a - b;});
       var actual = toSort.fire();

       var toString = new F.lazyChain([1, 3, 2]).invoke('sort').invoke('toString');
       var actualString = toString.fire();
       test.deepEqual(actual, [1, 3, 20]);
       test.equal(actualString, '1,2,3');
       test.done();
   },
   "should sort multiple arrays": function(test) {
       var sortedArray = _.map([[1, 3, 2], [4, 5, 3], [7, 100, 8]], function(arr) {
           return new F.lazyChain(arr).invoke('sort', function(a, b){return a - b}).fire();
       });
       test.deepEqual(sortedArray, [[1, 2, 3], [3, 4, 5], [7, 8, 100]]);
       test.done();
   },
   "should init array with empty string or special string": function(test) {
       var arr = new Array(3).join(',').split(',');
       var newArr = new Array(3 - 1);
       newArr.push('hello');
       var arr2 = newArr.join('hello,').split(',');

       test.deepEqual(arr, ['', '', '']);
       test.deepEqual(arr2, ['hello', 'hello', 'hello']);
       test.done();
   }
});
