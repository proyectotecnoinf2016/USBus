/**
 * Created by Lucia on 6/5/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('BusResource', BusResource);
    BusResource.$inject = ['$resource'];
    /* @ngInject */
    function BusResource($resource) {
        return {
            buses: function (token) {
                return $resource('/rest/api/:tenantId/bus/:busId', {tenantId:'@tenantId', busId: '@busId'}, {
                    query: {
                        method: 'GET',
                        isArray: true,
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
