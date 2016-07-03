/**
 * Created by Lucia on 7/2/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('role', role);
    /* @ngInject */
    function role() {
        return {
            getRoleToShow: function(role) {
                switch (role) {
                    case 'ADMINISTRATOR':
                        return 'Administrador';
                    case 'CASHIER':
                        return 'Cajero';
                    case 'DRIVER':
                        return 'Chofer';
                    case 'ASSISTANT':
                        return 'Guarda';
                    case 'MECHANIC':
                        return 'Mecanico';
                    case 'OTHER':
                        return 'Otro'
                }

                return this;
            },
            getRole: function(role) {
                switch (day) {
                    case 'Administrador':
                        return 'ADMINISTRATOR';
                    case 'Cajero':
                        return 'CASHIER';
                    case 'Chofer':
                        return 'DRIVER';
                    case 'Guarda':
                        return 'ASSISTANT';
                    case 'Mecanico':
                        return 'MECHANIC';
                    case 'Otro':
                        return 'OTHER'
                }

                return this;
            }

        };
    }
})();
