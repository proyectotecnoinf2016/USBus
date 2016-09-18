/**
 * Created by Lucia on 5/30/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('HomeController', HomeController);
    HomeController.$inject = ['$scope', '$rootScope', 'localStorage', '$location', '$window', '$mdDialog'];
    /* @ngInject */
    function HomeController($scope, $rootScope, localStorage, $location, $window, $mdDialog ) {
        $scope.redirectToHHRR = redirectToHHRR;
        $scope.redirectTo = redirectTo;
        $scope.showAlert = showAlert;
        $scope.showMenuOption = showMenuOption;

        $scope.token = null;

        $rootScope.$emit('options', '');
        $rootScope.$on('login', function(event, data) {
            if (typeof localStorage.getData('tenantName') !== 'undefined' && localStorage.getData('tenantName') != null) {
                $rootScope.tenantName = localStorage.getData('tenantName');
            }

            if (typeof localStorage.getData('token') !== 'undefined' && localStorage.getData('token') != null) {
                $scope.token = localStorage.getData('token');
            }
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


		function showMenuOption(path) {
            $scope.userRoles = localStorage.getData('userRoles');

            if (path == 'admin') {
                $scope.showAdmin = $scope.userRoles.includes('ADMINISTRATOR');
            }

            if (path == 'tickets') {
                $scope.showTickets = $scope.userRoles.includes('ADMINISTRATOR') || $scope.userRoles.includes('CASHIER');
            }

            if (path == 'schedule') {
                $scope.showSchedule = $scope.userRoles.includes('ASSISTANT') || $scope.userRoles.includes('DRIVER');
            }

            if (path == 'workshop') {
                $scope.showWorkshop = $scope.userRoles.includes('ADMINISTRATOR') || $scope.userRoles.includes('MECHANIC');
            }

            if (path == 'accounting') {
                $scope.showAccounting = $scope.userRoles.includes('ADMINISTRATOR') || $scope.userRoles.includes('CASHIER');
            }

            if (path == 'hhrr') {
                $scope.showhhrr = $scope.userRoles.includes('ADMINISTRATOR');
            }

            return false;
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
