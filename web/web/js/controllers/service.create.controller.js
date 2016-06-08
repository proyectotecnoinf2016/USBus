/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateServiceController', CreateServiceController);
    CreateServiceController.$inject = ['$scope', 'localStorage', 'ServiceResource', '$mdDialog'];
    /* @ngInject */
    function CreateServiceController($scope, localStorage, ServiceResource, $mdDialog) {
        $scope.createService = createService;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        function createService(route) {
            ServiceResource.save(route,function (resp) {
                showAlert('Exito!', 'Se ha creado su unidad de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al registrar el TENANT');
            } );
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
