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
                return $resource('/rest/api/:tenantId/busStop', {tenantId: '@tenantId'}, {
                    query: {
                        method: 'GET',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },
                    save: {
                        method: 'POST',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    }
                })
            }
        };
    }


})();
