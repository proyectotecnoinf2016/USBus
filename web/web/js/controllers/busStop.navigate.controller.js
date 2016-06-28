/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('BusStopController', BusStopController);
    BusStopController.$inject = ['$scope', '$mdDialog', 'BusStopResource', 'localStorage', '$rootScope'];
    /* @ngInject */
    function BusStopController($scope, $mdDialog, BusStopResource, localStorage, $rootScope) {
        $scope.showBusStop = showBusStop;
        $scope.createBusStop = createBusStop;
        $scope.deleteBusStop = deleteBusStop;
        $scope.showAlert = showAlert;

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.busStops = [];

        $rootScope.$emit('options', 'admin');


        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }
        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        BusStopResource.busStops(token).query({
            offset: 0,
            limit: 100,
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            $scope.busStops = result;

        });

        if ($scope.busStops.length == 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }

        function showBusStop(item, ev) {
            $mdDialog.show({
                controller : 'EditBusStopController',
                templateUrl : 'templates/busStop.edit.html',
                locals:{busStopToEdit: item, theme : $scope.theme}, //text va a ser usado para pasar el id del journey
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : true
            }).then(
                function(answer) {
                    alert(answer);
                    $scope.status = 'You said the information was "'
                        + answer + '".';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function createBusStop(ev) {
            $mdDialog.show({
                controller : 'CreateBusStopController',
                templateUrl : 'templates/busStop.create.html',
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : true,
                locals : {theme : $scope.theme}
            }).then(
                function(answer) {
                    $scope.status = 'Aca deberia hacer la query de nuevo';
                    BusStopResource.busStops(token).query({
                        offset: 0,
                        limit: 100,
                        tenantId: $scope.tenantId
                    }).$promise.then(function(result) {
                        console.log(result);
                        $scope.busStops = result;

                    });
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function deleteBusStop(item) {
            console.log(item);
            delete item["_id"];
            item.active = false;
            BusStopResource.busStops(token).delete({busStopId: item.id, tenantId: $scope.tenantId}, item).$promise.then(function(data){
                showAlert('Exito!','Se ha editado la Parada de forma exitosa');
                console.log(item);
            }, function(error){
                showAlert('Error!','Ocurrió un error al procesar su petición');
            });
            var index = 0;

            while (index < $scope.busStops.length && item.id != $scope.busStops[index].id) {
                index++;
            }

            if (index < $scope.busStops.length) {
                $scope.busStops.splice(index, 1);
            }

            if ($scope.busStops.length === 0) {
                $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
            }

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