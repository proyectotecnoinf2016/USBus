/**
 * Created by jpmartinez on 08/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('RegisterController', RegisterController);
    RegisterController.$inject = ['$scope','RegisterTenantResource','RegisterUserResource'];
    /* @ngInject */
    function RegisterController($scope,RegisterTenantResource,RegisterUserResource) {
        $scope.register = register;

        function register(tenant,user) {
            var ok = true;
            tenant.tenantId = 0;
            user.active = true;

            if ($scope.gender == 0) {
                user.gender = 'MALE'
            }
            else if ($scope.gender == 1) {
                user.gender = 'FEMALE';
            }
            else {
                user.gender = 'OTHER';
            }

            RegisterTenantResource.save(tenant,function (resp) {
                ok = true;
                console.log(resp);
            }, function (error) {
                ok = false;
                console.log(error);
                alert("Ocurrió un error al registrar el TENANT");

            } );
            if (ok){
                RegisterUserResource.save(user,function (respU) {
                    console.log(respU);
                },function (error) {
                    console.log(error);
                    alert("Ocurrió un error al registrar el USUARIO");
                });
            }
            //TODO si da error, borrar el tenant creado.

        }
    }
})();
