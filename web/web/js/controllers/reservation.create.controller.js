/**
 * Created by Lucia on 6/20/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('ReservationController', ReservationController);
    ReservationController.$inject = ['$scope', '$rootScope'];
    /* @ngInject */
    function ReservationController($scope, $rootScope) {
        $rootScope.$emit('options', 'tickets');

    }
})();