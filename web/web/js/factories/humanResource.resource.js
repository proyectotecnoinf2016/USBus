/**
 * Created by Lucia on 6/28/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('HumanResource', HumanResource);
    HumanResource.$inject = ['$resource'];
    /* @ngInject */
    function HumanResource($resource) {
        return {
            resources: function (token) {
                return $resource('/rest/api/:tenantId/hr', {tenantId: '@tenantId'}, {
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
