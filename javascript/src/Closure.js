var _ = require('underscore');
module.exports = {
    pluck: function(field) {
        return function(obj) {
            return obj && obj[field];
        }
    },
    O: (function (){
        var PRIVATE = 0;
        return {
            inc: function(num) {
                return PRIVATE += num;
            }
        };
    })(),
    mkUniqueString: function(start) {
        var COUNTER = start;
        return function(prefix) {
            return [prefix, COUNTER++].join('');
        };
    },
    fnull: function(func, defaultValue) {
        return function(memo, n) {
            var safeArgs = _.map([memo, n], function(arg) {
                return _.isNull(arg) || _.isUndefined(arg) ? defaultValue : arg;
            });
            return func.apply(null, safeArgs);
        };
    },
    check: function() {
        var validators = _.toArray(arguments);
        return function(obj) {
            return _.reduce(validators, function(memo, check){
                if(check(obj)) {
                    return memo;
                }
                return _.chain(memo).push(check.message).value();
            }, []);
        }
    },
    by: function(func, err) {
        var f = function(obj) {
            return func.apply(null, [obj]);
        }
        f.message = err;
        return f;
    },
    invoker: function(name, method) {
        return function(target) {
            if(!target) fail('must provide a target');

            var aMethod = target[name];
            if(aMethod && method === aMethod) {
                return aMethod.apply(target)
            }
        }
    }
};
