/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateBusController', CreateBusController);
    CreateBusController.$inject = ['$scope', 'localStorage', 'BusResource', '$mdDialog'];
    /* @ngInject */
    function CreateBusController($scope, localStorage, BusResource, $mdDialog) {
        $scope.createBus = createBus;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        function createBus(bus) {
            bus.status = 'ACTIVE';
            bus.tenantId = $scope.tenantId;
            var token = null;//localStorage.getData('token');
            if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
                token = localStorage.getData('token');
            }

            console.log(bus);

            BusResource.buses(token).save(bus,{
                tenantId: $scope.tenantId
            },function (resp) {
                console.log(resp);
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
