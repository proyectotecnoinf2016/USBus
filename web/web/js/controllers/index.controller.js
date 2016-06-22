/**
 * Created by jpmartinez on 12/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('IndexController', IndexController);
    IndexController.$inject = ['$scope', '$mdDialog', 'localStorage', '$location', '$rootScope'];
    /* @ngInject */
    function IndexController($scope, $mdDialog, localStorage, $location, $rootScope) {
        $scope.show = false;

		$scope.tenantName = 'USBus';
		$scope.userName = 'Invitado';

		$scope.login = login;
        $scope.redirectTo = redirectTo;

        if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
			$scope.tenantName = localStorage.getData('tenantName');
		}

		if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
			$scope.userName = localStorage.getData('userName');
		}

        $scope.menuOptions = [];

        $rootScope.$on('options', function (event, data) {
            var options = '';
            if (data == 'admin') {

                options = [{
                    name : "Planificar Viajes",
                    url  : "admin",
                    icon : ""
                } , {
                    name: "Administrar Usuarios"
                } , {
                    name: "Administrar Sucursales",
                    url : "admin/branch"
                } , {
                    name: "Administrar Unidades",
                    url : "admin/bus"
                } , {
                    name: "Administrar Paradas",
                    url : "admin/busStop"
                } , {
                    name: "Administrar Trayectos",
                    url : "admin/route"
                } , {
                    name: "Administrar Servicios",
                    url : "admin/service"
                } , {
                    name: "Personalizar Estilos",
                    url : "admin/styles"
                }];
            }

            if (data == 'tickets') {
                options = [{
                    name : "Pasajes",
                    url: "tickets",
                    icon: "settings"
                } , {
                    name: "Caja",
                    url : "tickets/window"
                } , {
                    name: "Inicio",
                    url : ""
                }];

            }

            $scope.menuOptions = [];

            if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
                $scope.tenantName = localStorage.getData('tenantName');
            }
            if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
                $scope.userName = localStorage.getData('userName');
            }

            if ($scope.tenantName !== 'USBus') {
                var i = 0;
                while (i < options.length) {
                    console.log(options[i].name);
                    $scope.menuOptions.push(options[i]);
                    i++;
                }
                console.log('menuOptions');
                console.log($scope.menuOptions);
            }
            else {
                $scope.menuOptions = [];
            }

        });


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
                    $rootScope.$broadcast('login', 'success');
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
            }
            url = url + redirectUrl;
            console.log('url');
            console.log(url);
            $location.path(url);
        }




        $scope.$on('Module',function(event, showMenu){

        })

    }
})();
