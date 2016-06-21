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
                controller: 'HomeController'
            })
            .when('/tenant/:tenantName/admin/bus', {
                templateUrl: 'templates/bus.navigate.html',
                controller: 'BusController'
            })
            .when('/tenant/:tenantName/admin/busStop', {
                templateUrl: 'templates/busStop.navigate.html',
                controller: 'BusStopController'
            })
            .when('/tenant/:tenantName/admin/route', {
                templateUrl: 'templates/route.navigate.html',
                controller: 'RouteController'
            })
            .when('/tenant/:tenantName/admin/service', {
                templateUrl: 'templates/service.navigate.html',
                controller: 'ServiceController'
            })
            .when('/tenant/:tenantName/admin/branch', {
                templateUrl: 'templates/branch.navigate.html',
                controller: 'BranchController'
            })
            .when('/tenant/:tenantName/admin/styles', {
                templateUrl: 'templates/tenant.styles.html',
                controller: 'TenantController'
            })
            .when('/tenant/:tenantName/admin', {
                templateUrl: 'templates/journey.navigate.html',
                controller: 'JourneyController'
            })
			.when('/tenant/:tenantName/tickets', {
                templateUrl: 'templates/journey.selection.html',
                controller: 'TicketsController'
            })
            .when('/tenant/:tenantName/tickets/window', {
                templateUrl: 'templates/window.navigate.html',
                controller: 'WindowController'
            })
			.when('/tenant/:tenantName/boxes', {
                templateUrl: 'templates/boxes.html',
                controller: 'HomeController'
            })
			.when('/tenant/:tenantName/workshop', {
                templateUrl: 'templates/workshop.html',
                controller: 'HomeController'
            })
			.when('/tenant/:tenantName/accounting', {
                templateUrl: 'templates/accounting.html',
                controller: 'HomeController'
            })
			.when('/tenant/:tenantName/humanResources', {
                templateUrl: 'templates/humanResources.html',
                controller: 'HomeController'
            })
            .otherwise({
                redirectTo: '/'
            });
    };
})();
