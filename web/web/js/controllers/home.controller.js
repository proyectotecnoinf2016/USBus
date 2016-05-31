/**
 * Created by Lucia on 5/30/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('HomeController', HomeController);
    HomeController.$inject = ['$rootScope', 'localStorage'];
    /* @ngInject */
    function HomeController($rootScope, localStorage) {
        $rootScope.show = typeof localStorage.getData('token') !== 'undefined' && localStorage.getData('token') != null;

        //TODO: la variable show depende de si el loquito esta dentro de un menu con muchas opciones o no

        if (typeof localStorage.getData('tenantName') !== 'undefined' && localStorage.getData('tenantName') != null) {
            $rootScope.tenantName = localStorage.getData('tenantName');
        }
    }
})();
