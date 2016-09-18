/**
 * Created by Lucia on 5/30/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('HomeController', HomeController);
    HomeController.$inject = ['$scope', '$rootScope', 'localStorage', '$location', '$window', '$mdDialog'];
    /* @ngInject */
    function HomeController($scope, $rootScope, localStorage, $location, $window, $mdDialog ) {
        showMenuOption();
        $scope.redirectToHHRR = redirectToHHRR;
        $scope.redirectToAccounting = redirectToAccounting;
        $scope.redirectTo = redirectTo;
        $scope.showAlert = showAlert;


        $scope.token = null;

        $rootScope.$emit('options', '');
        $rootScope.$on('login', function(event, data) {
            if (typeof localStorage.getData('tenantName') !== 'undefined' && localStorage.getData('tenantName') != null) {
                $rootScope.tenantName = localStorage.getData('tenantName');
            }

            if (typeof localStorage.getData('token') !== 'undefined' && localStorage.getData('token') != null) {
                $scope.token = localStorage.getData('token');
            }

            showMenuOption();
        })


        if (typeof localStorage.getData('tenantName') !== 'undefined' && localStorage.getData('tenantName') != null) {
            $rootScope.tenantName = localStorage.getData('tenantName');
        }

        if (typeof localStorage.getData('token') !== 'undefined' && localStorage.getData('token') != null) {
            $scope.token = localStorage.getData('token');
        }

        if (typeof localStorage.getData('userRoles') !== 'undefined' && localStorage.getData('userRoles') != null) {
            $scope.userRoles = localStorage.getData('userRoles');

        }


        $rootScope.$on('logout', function() {
            $scope.token = null;
        });


		function showMenuOption() {
                var roles = localStorage.getData('userRoles');
                if (roles==null || roles == "undefined"){
                    roles = '';
                }
                console.log(roles);
                console.log(roles.includes('ADMINISTRATOR'));
                console.log(roles.includes('CASHIER'));

                $scope.showAdmin = roles.includes('ADMINISTRATOR');
                $scope.showTickets = roles.includes('ADMINISTRATOR') || roles.includes('CASHIER');
                $scope.showSchedule = roles.includes('ASSISTANT') || roles.includes('DRIVER');
                $scope.showWorkshop = false;//roles.includes('ADMINISTRATOR') || roles.includes('MECHANIC');
                $scope.showAccounting = roles.includes('ADMINISTRATOR') || roles.includes('CASHIER');
                $scope.showhhrr = roles.includes('ADMINISTRATOR');
                console.log($scope.showTickets);

        }

		function redirectTo(path) {
			$location.url($location.path() + '/' + path);
		}

		function redirectToHHRR() {
            if (typeof localStorage.getData('humanResourcesURL') !== 'undefined' && localStorage.getData('humanResourcesURL') != 'null') {
                console.log(localStorage.getData('humanResourcesURL'));
                $window.open(localStorage.getData('humanResourcesURL'));
            }
            else {
                showAlert('Error!', 'No se ha configurado el área de Recursos Humanos. Por favor contacte un administrador.');
            }
        }
        function redirectToAccounting() {
            if (typeof localStorage.getData('accountingURL') !== 'undefined' && localStorage.getData('accountingURL') != 'null') {
                console.log(localStorage.getData('accountingURL'));
                $window.open(localStorage.getData('accountingURL'));
            }
            else {
                showAlert('Error!', 'No se ha configurado el área de Contabilidad. Por favor contacte un administrador.');
            }
        }

        function showAlert(title,content) {
            $mdDialog
                .show($mdDialog
                    .alert()
                    .parent(
                        angular.element(document
                            .querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title(title)
                    .content(content)
                    .ariaLabel('Alert Dialog Demo').ok('Cerrar'));

        };

    }
})();
