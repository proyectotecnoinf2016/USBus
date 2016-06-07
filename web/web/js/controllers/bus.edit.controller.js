/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditBusController', EditBusController);
    EditBusController.$inject = ['$scope', 'BusResource', '$mdDialog'];
    /* @ngInject */
    function EditBusController($scope, BusResource, $mdDialog) {
        $scope.busId = 0;
        $scope.tenantId = 0;
        

        $scope.cancel = cancel;
        $scope.showAlert = showAlert;

        $scope.bus = BusResource.get({
            id: $scope.busId,
            tenantId: $scope.tenantId
        });


        function updateBus(bus) {
            BusResource.update({id: bus.id, tenantId: $scope.tenantId}, bus).$promise.then(function(data){
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
