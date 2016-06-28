/**
 * Created by Lucia on 6/28/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('TenantResource', TenantResource);
    TenantResource.$inject = ['$resource'];
    /* @ngInject */
    function TenantResource($resource) {
        return {
            tenant: function (token) {
                return $resource('/rest/api/:tenantId/Tenant', {tenantId: '@tenantId'}, {
                    update: {
                        method: 'PUT',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    }
                })
            }
        };
    }
})();
