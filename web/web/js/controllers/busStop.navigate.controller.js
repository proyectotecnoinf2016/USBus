/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('BusStopController', BusStopController);
    BusStopController.$inject = ['$scope', '$mdDialog', 'BusStopResource', 'localStorage'];
    /* @ngInject */
    function BusStopController($scope, $mdDialog, BusStopResource, localStorage) {
        $scope.showBusStop = showBusStop;
        $scope.createBusStop = createBusStop;
        $scope.deleteBusStop = deleteBusStop;

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.busStops = [];


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

        if ($scope.busStops.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }




        /*BusStopResource.query({
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            var journeys = $scope.journeys.concat(result);
            $scope.journeys = journeys;
        });
        */


        function showBusStop(item, ev) {
            $mdDialog.show({
                controller : 'EditBusStopController',
                templateUrl : 'templates/busStop.edit.html',
                locals:{busStopToEdit: item}, //text va a ser usado para pasar el id del journey
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : true
            }).then(
                function(answer) {
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
                clickOutsideToClose : true
            }).then(
                function(answer) {
                    $scope.status = 'Aca deberia hacer la query de nuevo';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function deleteBusStop(text) {
            //TODO: ver si aca va el id, el name o quien (supongo que va el id nomas);
            /*
            bus.active = false;
            BusStopResource.update({id: bus.id, tenantId: $scope.tenantId}, bus).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
                console.log(style);
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
            */
            var index = 0;

            while (index < $scope.busStops.length && text != $scope.busStops[index].name) {
                index++;
            }

            if (index < $scope.busStops.length) {
                $scope.busStops.splice(index, 1);
            }

            if ($scope.busStops.length === 0) {
                $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
            }

        }

    }
})();