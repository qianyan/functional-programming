var _ = require('underscore');
var F = {
    project: function(obj, keys) {
        return _.map(obj, function(o) {
            return _.pick.apply(null, [o].concat(keys));
        });
    },
    rename: function(obj, newNames) {
        return _.reduce(newNames, function(memo, v, k) {
            if(_.has(obj, k)) {
                memo[v] = obj[k];
                return memo;
            }
            return memo;
        }, _.omit.apply(null, [obj].concat(_.keys(newNames))));
    },
    anyOf: function() {
        return _.reduce(arguments, function(truth, next) {
            return truth || next;
        });
    },
    allOf: function() {
        return _.reduce(arguments, function(truth, next) {
            return truth && next;
        });
    },
    as: function(args, newNames) {
        return _.map(args, function(obj) {
            return F.rename(obj, newNames);
        });
    }
};
module.exports  = F;
