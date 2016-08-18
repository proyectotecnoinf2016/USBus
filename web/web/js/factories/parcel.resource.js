/**
 * Created by Lucia on 6/7/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('ParcelResource', ParcelResource);
    ParcelResource.$inject = ['$resource'];
    /* @ngInject */
    function ParcelResource($resource) {
        return {
            parcels: function (token) {
                return $resource('/rest/api/:tenantId/parcel/:parcelId', {tenantId:'@tenantId', branchId: '@parcelId'}, {
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
