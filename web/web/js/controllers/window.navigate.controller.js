/**
 * Created by Lucia on 6/20/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('WindowController', WindowController);
    WindowController.$inject = ['$scope', '$rootScope'];
    /* @ngInject */
    function WindowController($scope, $rootScope) {
        $rootScope.$emit('options', 'tickets');
    }
})();