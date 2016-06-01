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
		$scope.userName = 'Lucia Salvarrey';
		
		$scope.openMenu = openMenu;
		$scope.login = login;
		
		if (localStorage.getData('tenantName') != null) {
			$scope.tenantName = localStorage.getData('tenantName');
		}
		
		if (localStorage.getData('userName') != null) {
			$scope.userName = localStorage.getData('userName');
		}
		
        $scope.openSideNav = function() {
            $mdSidenav('left').toggle();
        };
		
		function openMenu($mdOpenMenu, ev) {
			alert(ev);
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
    }
})();
