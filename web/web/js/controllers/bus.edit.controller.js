/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditBusController', EditBusController);
    EditBusController.$inject = ['$scope', 'BusResource', '$mdDialog', 'busToEdit', 'localStorage', 'theme'];
    /* @ngInject */
    function EditBusController($scope, BusResource, $mdDialog, busToEdit, localStorage, theme) {
        $scope.theme = theme;
        $scope.bus = busToEdit;
        $scope.tenantId = 0;
        

        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.updateBus = updateBus;

        /*$scope.bus = BusResource.get({
            id: $scope.busId,
            tenantId: $scope.tenantId
        });
        */

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }


        function updateBus(bus) {
            bus.tenantId = $scope.tenantId;
            delete bus["_id"];

            BusResource.buses(token).update({
                tenantId: $scope.tenantId,
                busId: bus.id
            }, bus,function (resp) {
                console.log(resp);
                showAlert('Exito!', 'Se ha editado su unidad de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al editar la Unidad');
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
