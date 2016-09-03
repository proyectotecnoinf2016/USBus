/**
 * Created by Lucia on 6/7/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('TicketResource', TicketResource);
    TicketResource.$inject = ['$resource'];
    /* @ngInject */
    function TicketResource($resource) {
        return {
                tickets: function (token) {
                return $resource('/rest/api/:tenantId/ticket/:ticketId', {tenantId:'@tenantId', ticketId: '@ticketId'}, {
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
