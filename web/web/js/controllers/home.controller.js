/**
 * Created by Lucia on 5/30/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('HomeController', HomeController);
    HomeController.$inject = ['$rootScope', 'localStorage'];
    /* @ngInject */
    function HomeController($rootScope, localStorage) {
        $rootScope.logged = typeof localStorage.getData('token') !== 'undefined' && localStorage.getData('token') != null;
    }
})();
