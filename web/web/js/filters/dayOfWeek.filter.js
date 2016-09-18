/**
 * Created by Lucia on 7/2/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .filter('dayOfWeek', dayOfWeek);
    /* @ngInject */
    function dayOfWeek() {
        return function(dayOfWeek) {
            console.log("Toy en el filtro me llego " + dayOfWeek)
                switch (dayOfWeek) {
                    case 'MONDAY':
                        return 'Lunes';
                    case 'TUESDAY':
                        return 'Martes';
                    case 'WEDNESDAY':
                        return 'Miércoles';
                    case 'THURSDAY':
                        return 'Jueves';
                    case 'FRIDAY':
                        return 'Viernes';
                    case 'SATURDAY':
                        return 'Sábado';
                    case 'SUNDAY':
                        return 'Domingo'
                }
            }

        };
})();
