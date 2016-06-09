/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('BusController', BusController);
    BusController.$inject = ['$scope', '$mdDialog', 'BusResource', 'localStorage'];
    /* @ngInject */
    function BusController($scope, $mdDialog, BusResource, localStorage) {
        $scope.showBus = showBus;
        $scope.createBus = createBus;
        $scope.deleteBus = deleteBus;

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.buses = [];

        if ($scope.buses.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }

        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        BusResource.buses(token).query({
            offset: 0,
            limit: 100,
            status: 'ACTIVE',
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            $scope.buses = $scope.buses.concat(result);

        });



        function showBus(item, ev) {
            $mdDialog.show({
                controller : 'EditBusController',
                templateUrl : 'templates/bus.edit.html',
                locals:{busToEdit: item}, //text va a ser usado para pasar el id del journey
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
                controller : 'CreateBusController',
                templateUrl : 'templates/bus.create.html',
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