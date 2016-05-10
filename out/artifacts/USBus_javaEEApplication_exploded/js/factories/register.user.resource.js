/**
 * Created by jpmartinez on 08/05/16.
 */
(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('RegisterUserResource', RegisterUserResource);
    RegisterUserResource.$inject = ['$resource'];
    /* @ngInject */
    function RegisterUserResource($resource) {
        return $resource('/usbus/api/register/user');
    }
})();
