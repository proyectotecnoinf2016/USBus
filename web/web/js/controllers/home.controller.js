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
        })


        if (typeof localStorage.getData('tenantName') !== 'undefined' && localStorage.getData('tenantName') != null) {
            $rootScope.tenantName = localStorage.getData('tenantName');
        }

        if (typeof localStorage.getData('token') !== 'undefined' && localStorage.getData('token') != null) {
            $scope.token = localStorage.getData('token');
        }


        $rootScope.$on('logout', function() {
            $scope.token = null;
        });

		
		$scope.redirectTo = redirectTo;

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
