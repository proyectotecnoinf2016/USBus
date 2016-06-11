/**
 * Created by Lucia on 6/5/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('BusStopResource', BusStopResource);
    BusStopResource.$inject = ['$resource'];
    /* @ngInject */

    function BusStopResource($resource) {
        return {
            busStops: function (token) {
                return $resource('/rest/api/:tenantId/busstop/:busStopId', {tenantId: '@tenantId', busStopId: '@busStopId'}, {
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
