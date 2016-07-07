/**
 * Created by Lucia on 6/5/2016.
 */
(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('JourneyResource', JourneyResource);
    JourneyResource.$inject = ['$resource'];
    /* @ngInject */
    function JourneyResource($resource) {
        return {
            journeys: function (token) {
                return $resource('/rest/api/:tenantId/journey/:journeyId', {tenantId: '@tenantId', journeyId: '@journeyId'}, {
                    query: {
                        method: 'GET',
                        isArray: true,
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },
                    get: {
                        method: 'GET',
                        url: '/rest/api/:tenantId/journey/:journeyId/price',
                        params: {tenantId: '@tenantId', journeyId: '@journeyId'},
                        isArray: false,
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },
                    queryOne: {
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
