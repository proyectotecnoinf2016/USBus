/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateBusStopController', CreateBusStopController);
    CreateBusStopController.$inject = ['$scope', 'localStorage', 'BusStopResource','$mdDialog'];
    /* @ngInject */
    function CreateBusStopController($scope, localStorage, BusStopResource,$mdDialog) {
        $scope.createBusStop = createBusStop;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');

        }
        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }
        alert($scope.tenantId);

        function createBusStop(busStop) {
            busStop.tenantId = $scope.tenantId;
            BusStopResource.busStops(token).save({tenantId: $scope.tenantId },busStop, function(){
                showAlert('Exito!', 'Se ha creado la parada de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al crear la parada');
            });
        }

        function showAlert(title, content) {
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
