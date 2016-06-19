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

        $scope.menuOptions = [];

		$scope.login = login;
        $scope.redirectTo = redirectTo;

        if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
			$scope.tenantName = localStorage.getData('tenantName');
		}

		if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
			$scope.userName = localStorage.getData('userName');
		}

        $rootScope.$on('options', function (event, data) {
            $scope.menuOptions = [];
            var i = 0;
            while (i < data.length) {
                console.log(data[i].name);
                $scope.menuOptions.push(data[i]);
                i++;
            }
            console.log('menuOptions');
            console.log($scope.menuOptions);
        });

        $rootScope.$on('menuOption', function (event, data) {
            if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
                $scope.tenantName = localStorage.getData('tenantName');
            }
            if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
                $scope.userName = localStorage.getData('userName');
            }
            console.log(data); // 'Data to send'
            if ($scope.tenantName !== 'USBus') {
                if (data) {
                    $scope.show = true;
                }
                else {
                    $scope.show = false;
                }
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
            alert(redirectUrl);
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
            $location.path(url);
        }




        $scope.$on('Module',function(event, showMenu){

        })

    }
})();
