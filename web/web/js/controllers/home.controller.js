/**
 * Created by Lucia on 5/30/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('HomeController', HomeController);
    HomeController.$inject = ['$scope', '$rootScope', 'localStorage', '$location', '$window'];
    /* @ngInject */
    function HomeController($scope, $rootScope, localStorage, $location, $window) {
        $scope.redirectToHHRR = redirectToHHRR;

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
            $window.open(localStorage.getData('humanResourcesURL'));
        }
    }
})();
