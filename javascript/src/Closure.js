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
    },
    dispatch: function() {
        var funcs = _.toArray(arguments);
        return function(target) {
            for(var i=0; i<funcs.length; i++) {
                var ret = funcs[i].apply(null, [target]); 
                if(!_.isEmpty(ret)) {
                    return ret;
                }
            }
        };
    },
    leftCurryDiv: function(n) {
        return function(d) {
            return n / d;
        };
    },
    rightCurryDiv: function(d) {
        return function(n) {
            return n / d;
        };
    },
    curry: function(func) {
        return function(arg) {
            return func(arg);
        };
    },
    curry2: function(func) {
        return function(secondArg) {
            return function(firstArg) {
                return func(firstArg, secondArg);
            };
        };
    },
    cat: function(){
        var head = _.first(arguments);

        return head.concat.apply(head, _.rest(arguments));
    },
    cycle: function(times, arr) {
        if(times <= 0) {
            return [];
        }
        return this.cat(arr, this.cycle(times-1, arr));
    },
    unzip: function(arr) {
        return _.zip.apply(_, arr);
    },
    flat: function(arr) {
        if(_.isArray(arr)) {
           return this.cat.apply(null, _.map(arr, function(e){
               return this.flat.call(this, e);
           }.bind(this)));
        }
        return [arr];
    },
    deepClone: function(obj){
        if(!_.isObject(obj)) {
            return obj;
        }

        var temp = new obj.constructor();
        for(var key in obj) {
            if(obj.hasOwnProperty(key)) {
                temp[key] = this.deepClone(obj[key]);
            }
        }

        return temp;
    },
    visit: function(mapFunc, resultFunc, arr) {
        if(!_.isArray(arr)) {
            return resultFunc(arr);
        }
        return resultFunc(_.map(arr, mapFunc));
    },
    generator: function(seed, current, step) {
        return {
            head: current(seed),
            tail: function(){return this.generator(step(seed), current, step)}
            .bind(this)
        };
    },
    partial: function(func, arg) {
        return function() {
            var args = [arg].concat(_.toArray(arguments));
            return func.apply(func, args);
        }
    },
    skipTake: function(n, arr) {
        return _.filter(arr, function(value, index){
            return index % n === 0;
        });
    },
    pipeline: function(seed) {
        return _.reduce(_.rest(arguments), function(r, f) {
            return f(r);
        }, seed);
    },
    actions: function(acts, done) {
        return function(value) {
            return done(_.reduce(acts, function(r, f) {
                return r.concat(f(_.last(r)));
            }, [value]));
        };
    }
};
