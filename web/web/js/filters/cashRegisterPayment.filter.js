/**
 * Created by Lucia on 7/2/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .filter('cashPayment', cashPayment);
    /* @ngInject */
    function cashPayment() {
        return function(cashPayment) {
            console.log("Toy en el filtro me llego " + cashPayment)
                switch (cashPayment) {
                    case 'CASH':
                        return 'Efectivo';
                    case 'CREDIT':
                        return 'Crédito';
                    case 'DEBIT':
                        return 'Débito';
                    case 'PAYPAL':
                        return 'PayPal';
                    case 'CHECK':
                        return 'Cheque';
                    case 'OTHER':
                        return 'Otros';
                }
            }

        };
})();
