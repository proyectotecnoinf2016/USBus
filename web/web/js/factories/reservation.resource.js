/**
 * Created by Lucia on 6/7/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('ReservationResource', ReservationResource);
    ReservationResource.$inject = ['$resource'];
    /* @ngInject */
    function ReservationResource($resource) {
        return {
            reservations: function (token) {
                return $resource('/rest/api/:tenantId/reservation/:reservationId', {tenantId:'@tenantId', reservationId: '@reservationId'}, {
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
