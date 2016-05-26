/**
 * Created by jpmartinez on 26/05/16.
 */
(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('localStorage', localStorage);
    localStorage.$inject = ['$window','$rootScope'];
    /* @ngInject */
    function localStorage($window, $rootScope) {
        angular.element($window).on('storage', function(event) {
            if (event.key === 'my-storage') {
                $rootScope.$apply();
            }
        });
        return {
            setData: function(key,val) {
                $window.localStorage && $window.localStorage.setItem(key, val);
                return this;
            },
            getData: function(key) {
                return $window.localStorage && $window.localStorage.getItem(key);
            }
        };
    }
})();
