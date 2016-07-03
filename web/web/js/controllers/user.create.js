/**
 * Created by Lucia on 6/21/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateUsersController', CreateUsersController);
    CreateUsersController.$inject = ['localStorage'];
    /* @ngInject */
    function CreateUsersController(localStorage) {
        $scope.users = [];
        $scope.message = 'No se encontraron Usuarios para mostrar';

        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        HumanResource.resources(token).query({
            offset: 0,
            limit: 100,
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            $scope.users = result;

        });
    }
})();