/**
 * Created by Lucia on 6/21/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateUsersController', CreateUsersController);
    CreateUsersController.$inject = ['localStorage', '$scope', 'HumanResource', '$mdDialog', 'theme'];
    /* @ngInject */
    function CreateUsersController(localStorage, $scope, HumanResource, $mdDialog, theme) {
        $scope.cancel = cancel;
        $scope.createUser = createUser;
        $scope.showAlert = showAlert;
        $scope.nextTab = nextTab;


        $scope.theme = theme;
        $scope.selectedIndex = 0;
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





        function createUser(item) {
            HumanResource.resources(token).save({tenantId: $scope.tenantId },item, function(){
                showAlert('Exito!', 'Se ha creado el servicio de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error');
            } );
        }


        function nextTab(route) {

            addRouteStopsToArray(route);
            var index = ($scope.selectedIndex == $scope.max) ? 0 : $scope.selectedIndex + 1;
            $scope.selectedIndex = index;
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

        function cancel() {
            $mdDialog.cancel();
        };

    }
})();