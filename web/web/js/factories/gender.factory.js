/**
 * Created by Lucia on 7/2/2016.
 */


(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('gender', gender);
    /* @ngInject */
    function gender() {
        return {
            getGenderToShow: function(gender) {
                switch (gender) {
                    case 'FEMALE':
                        return 'Mujer';
                    case 'MALE':
                        return 'Hombre';
                    case 'OTHER':
                        return 'Otro';
                }

                return this;
            },
            getGender: function(gender) {
                switch (gender) {
                    case 'Mujer':
                        return 'FEMALE';
                    case 'Hombre':
                        return 'MALE';
                    case 'Otro':
                        return 'OTHER';
                }

                return this;
            }

        };
    }
})();
