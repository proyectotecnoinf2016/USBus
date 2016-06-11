/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditBusStopController', EditBusStopController);
    EditBusStopController.$inject = ['$scope', 'BusStopResource', '$mdDialog', 'busStopToEdit', 'localStorage'];
    /* @ngInject */
    function EditBusStopController($scope, BusStopResource, $mdDialog, busStopToEdit, localStorage) {
        $scope.busStop = busStopToEdit;
        $scope.tenantId = 0;
        

        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.updateBusStop = updateBusStop;
/*
        $scope.busStop = BusStopResource.get({
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
        console.log($scope.tenantId);

        function updateBusStop(item) {
            console.log(item);
            delete item["_id"];
            BusStopResource.busStops(token).update({busStopId: item.id, tenantId: $scope.tenantId}, item).$promise.then(function(data){
                showAlert('Exito!','Se ha editado la Parada de forma exitosa');
                console.log(item);
            }, function(error){
                showAlert('Error!','Ocurrió un error al procesar su petición');
            });
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
