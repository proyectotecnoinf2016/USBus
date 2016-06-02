/**
 * Created by jpmartinez on 12/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('IndexController', IndexController);
    IndexController.$inject = ['$scope','$mdSidenav', '$mdDialog', '$rootScope', 'localStorage'];
    /* @ngInject */
    function IndexController($scope,$mdSidenav, $mdDialog, $rootScope, localStorage) {
		var originatorEv;
		
        $rootScope.show = false;
		$scope.tenantName = 'USBus';
		$scope.userName = 'Invitado';

        $scope.messages = [{
            name : "Planificar Viajes"
        } , {
            name: "Administrar Usuarios"
        } , {
            name: "Administrar Sucursales"
        } , {
            name: "Administrar Cajas"
        } , {
            name: "Administrar Unidades"
        } , {
            name: "Administrar Paradas"
        } , {
            name: "Administrar Trayectos"
        } , {
            name: "Administrar Servicios"
        } , {
            name: "Personalizar Estilos"
        }];
		
		$scope.openMenu = openMenu;
		$scope.login = login;
        $scope.sampleAction = sampleAction;

        if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
			$scope.tenantName = localStorage.getData('tenantName');
		}
		
		if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
			$scope.userName = localStorage.getData('userName');
		}
		
        $scope.openSideNav = function() {
            $mdSidenav('left').toggle();
        };
		
		function openMenu($mdOpenMenu, ev) {
			originatorEv = ev;
			$mdOpenMenu(ev);
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
        }
        ;


        function sampleAction(name, ev) {
            $mdDialog.show($mdDialog.alert()
                .title(name)
                .textContent('Start learning "' + name + '!')
                .ok('OK')
                .targetEvent(ev)
            );
        };

    }
})();
