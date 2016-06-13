/**
 * Created by Lucia on 6/7/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('ServiceResource', ServiceResource);
    ServiceResource.$inject = ['$resource'];
    /* @ngInject */
    function ServiceResource($resource) {
        return {
            services: function (token) {
                return $resource('/rest/api/:tenantId/service/:serviceId', {tenantId: '@tenantId', serviceId: '@serviceId'}, {
                    query: {
                        method: 'GET',
                        isArray:true,
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },
                    save: {
                        method: 'POST',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },
                    update: {
                        method: 'PUT',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },
                    delete: {
                        method: 'DELETE',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    }
                })
            }
        };
    }
})();
