
(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('LoginUserResource', LoginUserResource);
    LoginUserResource.$inject = ['$resource'];
    /* @ngInject */
    function LoginUserResource($resource) {

        /*http://localhost:8080/rest/api/{tenantId}/busstop?offset=xxx&limit=yyy*/
        return $resource('/rest/api/authentication', {
            'Login': {
                method: 'POST'
            }
        });
    }
})();
