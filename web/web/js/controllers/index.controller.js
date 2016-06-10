/**
 * Created by jpmartinez on 12/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('IndexController', IndexController);
    IndexController.$inject = ['$scope', '$mdDialog', 'localStorage', '$location'];
    /* @ngInject */
    function IndexController($scope, $mdDialog, localStorage, $location) {
		$scope.show = localStorage.getData('showMenu');

		$scope.tenantName = 'USBus';
		$scope.userName = 'Invitado';

        $scope.messages = [{
            name : "Planificar Viajes",
            url  : "journeys"
        } , {
            name: "Administrar Usuarios"
        } , {
            name: "Administrar Sucursales",
            url : "branch"
        } , {
            name: "Administrar Unidades",
            url : "bus"
        } , {
            name: "Administrar Paradas",
            url : "busStop"
        } , {
            name: "Administrar Trayectos",
            url : "route"
        } , {
            name: "Administrar Servicios",
            url : "service"
        } , {
            name: "Personalizar Estilos"
        }];

		$scope.login = login;
        $scope.redirectTo = redirectTo;

        if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
			$scope.tenantName = localStorage.getData('tenantName');
		}

		if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
			$scope.userName = localStorage.getData('userName');
		}

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
        };

        function redirectTo(redirectUrl) {
            var urlArray = $location.path().split('/');
            console.log(urlArray);
            var url = '';
            var i = 0;

            while (i < urlArray.length && urlArray[i] != $scope.tenantName) {
                url = url + urlArray[i] + '/';
                i++;
            }

            if (i < urlArray.length) {
                url = url + urlArray[i] + '/';
                url = url + urlArray[i + 1] + '/'
            }
            url = url + redirectUrl;
            console.log(url);
            $location.path(url);
        }

    }
})();
