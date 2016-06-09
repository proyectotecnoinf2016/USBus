/**
 * Created by jpmartinez on 08/05/16.
 */
(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('RegisterTenantResource', RegisterTenantResource);
    RegisterTenantResource.$inject = ['$resource'];
    /* @ngInject */
    function RegisterTenantResource($resource) {
        return $resource('/rest/api/register/tenant');
    }
})();
