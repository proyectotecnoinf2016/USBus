(function() {
    'use strict';
    angular
        .module('USBus')
        .config(config);
    config.$inject = ['$routeProvider'];
    /* @ngInject */
    function config($routeProvider) {
        $routeProvider
                .when('/', {
                    templateUrl: 'templates/login.html',
                    controller: 'LoginController'
                })
                .otherwise({
                    redirectTo: '/'
                });
        };
})();
