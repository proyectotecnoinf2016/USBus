/**
 * Created by Lucia on 5/30/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('HomeController', HomeController);
    HomeController.$inject = ['$scope', '$rootScope', 'localStorage', '$location'];
    /* @ngInject */
    function HomeController($scope, $rootScope, localStorage, $location) {
		$rootScope.show = true;
        /*$rootScope.show = typeof localStorage.getData('token') !== 'undefined' && localStorage.getData('token') != null;
*/
        //TODO: la variable show depende de si el loquito esta dentro de un menu con muchas opciones o no

        if (typeof localStorage.getData('tenantName') !== 'undefined' && localStorage.getData('tenantName') != null) {
            $rootScope.tenantName = localStorage.getData('tenantName');
        }
		
		$scope.redirectTo = redirectTo;
		
		function redirectTo(path) {
			$location.url($location.path() + '/' + path);
		}
    }
})();
