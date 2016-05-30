(function () {
    'use strict';
    angular
        .module('usbus')
        .config(config);
    config.$inject = ['$routeProvider'];
    /* @ngInject */
    function config($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'templates/register.html',
                controller: 'RegisterController'
            })
            .when('/tenant/:tenantName', {
                templateUrl: 'templates/home.html',
                controller: 'LoginController'
            })
            .otherwise({
                redirectTo: '/'
            });
    };
})();
