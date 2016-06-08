/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateBusStopController', CreateBusStopController);
    CreateBusStopController.$inject = ['$scope', 'localStorage', 'BusStopResource'];
    /* @ngInject */
    function CreateBusStopController($scope, localStorage, BusStopResource) {
        $scope.createBusStop = createBusStop;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        function createBusStop(busStop) {
            BusStopResource.save(busStop,function (resp) {
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
