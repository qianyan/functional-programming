var _ = require('underscore');

module.exports = {
    pluck: function(field) {
        return function(obj) {
            return obj && obj[field];
        }
    }
};
