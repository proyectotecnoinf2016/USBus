
(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('LoginUserResource', LoginUserResource);
    LoginUserResource.$inject = ['$resource'];
    /* @ngInject */
    function LoginUserResource($resource) {
        return $resource('/usbus/api/authentication');
    }
})();
