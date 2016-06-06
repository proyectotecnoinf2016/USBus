/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('BusController', BusController);
    BusController.$inject = ['$scope', '$mdDialog', 'BusResource'];
    /* @ngInject */
    function BusController($scope, $mdDialog, BusResource) {
        $scope.showBus = showBus;
        $scope.createBus = createBus;
        $scope.deleteBus = deleteBus;

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.buses = [{
            'name': '1'
        }, {
            'name': '2'
        }];

        if ($scope.buses.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }


        /*BusResource.query({
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            var journeys = $scope.journeys.concat(result);
            $scope.journeys = journeys;
        });
        */


        function showBus(text, ev) {
            $mdDialog.show({
                controller : 'EditBusController',
                templateUrl : 'templates/bus.edit.html',
                locals:{busId: text}, //text va a ser usado para pasar el id del journey
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

        function createBus(ev) {
            $mdDialog.show({
                controller : 'EditBusController',
                templateUrl : 'templates/bus.edit.html',
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

        function deleteBus(text) {
            //TODO: ver si aca va el id, el name o quien (supongo que va el id nomas);
            /*
            bus.active = false;
            BusResource.update({id: bus.id, tenantId: $scope.tenantId}, bus).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
                console.log(style);
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
            */
            var index = 0;

            while (index < $scope.buses.length && text != $scope.buses[index].name) {
                index++;
            }

            if (index < $scope.buses.length) {
                $scope.buses.splice(index, 1);
            }

            if ($scope.buses.length === 0) {
                $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
            }

        }

    }
})();