/**
 * Created by JPMartinez on 6/7/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('CashRegisterResource', CashRegisterResource);
    CashRegisterResource.$inject = ['$resource'];
    /* @ngInject */
    function CashRegisterResource($resource) {
        return {
            cashRegister: function (token) {
                return $resource('/rest/api/:tenantId/cashregister/:cashRegisterId', {tenantId: '@tenantId', cashRegisterId: '@cashRegisterId'}, {
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
