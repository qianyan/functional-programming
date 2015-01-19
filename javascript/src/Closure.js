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
    }
};
