/**
 * Created by Lucia on 7/2/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('dayOfWeek', dayOfWeek);
    /* @ngInject */
    function dayOfWeek() {
        return {
            getDay: function(dayOfWeek) {
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

                return this;
            },
            getDayOfWeek: function(day) {
                switch (day) {
                    case 'Lunes':
                        return 'MONDAY';
                    case 'Martes':
                        return 'TUESDAY';
                    case 'Miércoles':
                        return 'WEDNESDAY';
                    case 'Jueves':
                        return 'THURSDAY';
                    case 'Viernes':
                        return 'FRIDAY';
                    case 'Sábado':
                        return 'SATURDAY';
                    case 'Domingo':
                        return 'SUNDAY'
                }

                return this;
            }

        };
    }
})();
