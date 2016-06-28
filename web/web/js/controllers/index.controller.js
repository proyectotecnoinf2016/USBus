/**
 * Created by jpmartinez on 12/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('IndexController', IndexController);
    IndexController.$inject = ['$scope', '$mdDialog', 'localStorage', '$location', '$rootScope'];
    /* @ngInject */
    function IndexController($scope, $mdDialog, localStorage, $location, $rootScope) {
        $scope.theme = 'redpink';
        
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

        $rootScope.$on('theme', function (event, data) {
            $scope.theme = data;
            //$rootScope.$broadcast('customTheme', data);
        });


        $scope.menuOptions = [];

        $rootScope.$on('options', function (event, data) {
            var options = '';
            if (data == 'admin') {

                options = [{
                    name : "Planificar Viajes",
                    url  : "admin",
                    icon : "event_seat"
                } , {
                    name: "Administrar Usuarios",
                    url  : "admin/users",
                    icon : "perm_identity"
                } , {
                    name: "Administrar Sucursales",
                    url : "admin/branch",
                    icon: "add_to_queue"
                } , {
                    name: "Administrar Unidades",
                    url : "admin/bus",
                    icon: "directions_bus"
                } , {
                    name: "Administrar Paradas",
                    url : "admin/busStop",
                    icon: "store_mall_directory"
                } , {
                    name: "Administrar Rutas",
                    url : "admin/route",
                    icon: "terrain"
                } , {
                    name: "Administrar Servicios",
                    url : "admin/service",
                    icon: "subway"
                } , {
                    name: "Personalizar Estilos",//<i class="material-icons">format_color_fill</i>
                    url : "admin/styles",
                    icon: "format_color_fill"
                }];
            }

            if (data == 'tickets') {
                options = [{
                    name : "Pasajes",
                    url: "tickets",
                    icon: "airline_seat_recline_normal" //<i class="material-icons">add_shopping_cart</i><i class="material-icons">airline_seat_recline_normal</i>
                } , {
                    name: "Caja",
                    url : "tickets/window",
                    icon: "account_balance_wallet"
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
