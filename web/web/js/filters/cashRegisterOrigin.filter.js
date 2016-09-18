/**
 * Created by Lucia on 7/2/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .filter('cashOrigin', cashOrigin);
    /* @ngInject */
    function cashOrigin() {
        return function(cashOrigin) {
            console.log("Toy en el filtro me llego " + cashOrigin)
                switch (cashOrigin) {
                    case 'TICKET':
                        return 'Pasaje';
                    case 'PARCEL':
                        return 'Encomienda';
                    case 'CASH_REGISTER':
                        return 'Caja';
                    case 'BUS':
                        return 'Ómnibus';
                    case 'CLIENT':
                        return 'Cliente';
                }
            }

        };
})();
