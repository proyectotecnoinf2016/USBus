/**
 * Created by jpmartinez on 12/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('IndexController', IndexController);
    IndexController.$inject = ['$scope','$mdSidenav'];
    /* @ngInject */
    function IndexController($scope,$mdSidenav) {
        $scope.openSideNav = function() {
            $mdSidenav('left').toggle();
        };
    }
})();
