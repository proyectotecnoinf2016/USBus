/**
 * Created by Lucia on 6/5/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('RouteResource', RouteResource);
    RouteResource.$inject = ['$resource'];
    /* @ngInject */

    function RouteResource($resource) {
        return {
            routes: function (token) {
                return $resource('/rest/api/tenant/:tenantId', {id:'@id', tenantId:'@tenantId'}, {
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
