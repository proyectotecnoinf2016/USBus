/**
 * Created by Lucia on 7/2/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .filter('cashType', cashType);
    /* @ngInject */
    function cashType() {
        return function(cashType) {
            console.log("Toy en el filtro me llego " + cashType)
                switch (cashType) {
                    case 'WITHDRAWAL':
                        return 'Salida';
                    case 'ENTRY':
                        return 'Entrada';
                    case 'CASH_COUNT':
                        return 'Arqueo';
                    case 'CASH_INIT':
                        return 'Apertura';
                    case 'CASH_CLOSURE':
                        return 'Cierre';
                }
            }

        };
})();
