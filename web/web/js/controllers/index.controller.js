/**
 * Created by jpmartinez on 12/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('IndexController', IndexController);
    IndexController.$inject = ['$scope','$mdSidenav', '$mdDialog', '$rootScope'];
    /* @ngInject */
    function IndexController($scope,$mdSidenav, $mdDialog, $rootScope) {
        $rootScope.logged = false;
        
        $scope.openSideNav = function() {
            $mdSidenav('left').toggle();
        };

        $scope.login = login;

        function login(ev) {
            $mdDialog.show({
                controller : 'LoginController',
                templateUrl : 'templates/login.html',

                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : true
            }).then(
                function(answer) {
                    $scope.status = 'You said the information was "'
                        + answer + '".';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        }
        ;
    }
})();
