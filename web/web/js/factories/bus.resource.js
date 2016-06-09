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
    /*function BusResource($resource, token) {
        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        return $resource('/rest/api/:tenantId/bus', {id:'@id', tenantId:'@tenantId'}, {
            'update': {
                method: 'PUT'
            },
            'query': {
                method: 'GET',
                headers: 'Authorization: Bearer ' + token
            }
        });

    }*/

    function BusResource($resource) {
        return {
            buses: function (token) {
                return $resource('/rest/api/:tenantId/bus', {tenantId:'@tenantId'}, {
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
