var _ = require('underscore');
module.exports = {
    select: function(obj, keys) {
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
            return this.rename(obj, newNames);
        }.bind(this));
    },
    restrict: function(objs, pred) {
        return _.filter(objs, pred);
    },
    repeat: function(times, value) {
        return _.map(_.range(times), function(){
            return value;
        });
    },
    repeatedly: function(times, func) {
        return _.map(_.range(times), func);
    },
    stringReverse: function(str) {
        return !_.isString(str) ? undefined : str.split('').reverse().join('');
    },
    doWhen: function(expr, fun) {
        if(expr) {
           return fun.apply(null);
        }
    },
    lazyChain: function(obj) {
        this._calls = [];
        this._target = obj;
        this.invoke = function(methodName) {
            var args = _.rest(arguments);
            this._calls.push(function(target) {
                var executor = target[methodName];
                return executor.apply(target, args);
            });
            return this;
        };
        
        this.fire = function() {
            return _.reduce(this._calls, function(v, f) {
                return f(v);
            }, this._target);
        };
    }
};
