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
    }
};
