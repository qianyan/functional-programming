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
    })()
};
