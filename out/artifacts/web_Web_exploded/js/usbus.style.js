/**
 * Created by jpmartinez on 09/05/16.
 */
(function() {
    'use strict';
    angular
        .module('usbus')
        .config(config);
    config.$inject = ['$mdThemingProvider'];
    /* @ngInject */
    function config($mdThemingProvider) {

        $mdThemingProvider.theme('dark', 'default')
            .primaryPalette('yellow')
            .dark();
    }
})();
 