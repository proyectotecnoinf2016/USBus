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
                return $resource('/rest/api/:tenantId/journey', {tenantId: '@tenantId'}, {
                    query: {
                        method: 'GET',
                        url: '/rest/api/:tenantId/journey/get/jbyDateOriginAndDestination',
                        params: {tenantId: '@tenantId'},
                        isArray:true,
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },
                    /*query: {
                        method: 'GET',
                        headers: {
                            'Authorization': 'Bearer ' + token
                        }
                    },*/
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
