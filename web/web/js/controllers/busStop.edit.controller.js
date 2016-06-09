/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditBusStopController', EditBusStopController);
    EditBusStopController.$inject = ['$scope', 'BusStopResource', '$mdDialog', 'busStopToEdit'];
    /* @ngInject */
    function EditBusStopController($scope, BusStopResource, $mdDialog, busStopToEdit) {
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

        function updateBusStop(item) {
            BusStopResource.update({id: item.id, tenantId: $scope.tenantId}, item).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
                console.log(style);
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
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
