/**
 * Created by jpmartinez on 08/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('RegisterController', RegisterController);
    RegisterController.$inject = ['RegisterTenantResource','RegisterUserResource', '$scope', '$mdDialog', '$location', '$window', 'localStorage'];
    /* @ngInject */
    function RegisterController(RegisterTenantResource,RegisterUserResource, $scope, $mdDialog, $location, $window, localStorage) {
        $scope.register = register;
        $scope.showAlert = showAlert;

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
				localStorage.setData('tenantId', tenant.name);
            }, function (error) {
                ok = false;
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al registrar el TENANT');

            } );
            if (ok){
                RegisterUserResource.save(user,function (respU) {
                    console.log(respU);
                    showAlert('Exito!', 'Se ha creado su empresa virtual de forma exitosa');
					localStorage.setData('userName', user.name);
                    $window.location.href = $location.$$absUrl + 'tenant/' + tenant.name;
                },function (error) {
                    console.log(error);
					localStorage.setData('tenantId', '');
					localStorage.setData('userName', '');
                    showAlert('Error!', 'Ocurrió un error al registrar el USUARIO');
                });
            }
            //TODO si da error, borrar el tenant creado.
        }

        function showAlert(title,content) {
            $mdDialog
                .show($mdDialog
                    .alert()
                    .parent(
                        angular.element(document
                            .querySelector('#popupContainer')))
                    .clickOutsideToClose(true)
                    .title(title)
                    .content(content)
                    .ariaLabel('Alert Dialog Demo').ok('Cerrar'));

        };
    }
})();
