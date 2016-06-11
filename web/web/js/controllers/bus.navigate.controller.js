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
            busStatus: 'ACTIVE',
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            $scope.buses = result;

        });

        if ($scope.buses.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }


        function showBus(item, ev) {
            $mdDialog.show({
                controller : 'EditBusController',
                templateUrl : 'templates/bus.edit.html',
                locals:{busToEdit: item},
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
                    BusResource.buses(token).query({
                        offset: 0,
                        limit: 100,
                        tenantId: $scope.tenantId
                    }).$promise.then(function(result) {
                        console.log(result);
                        $scope.buses = result;

                    });
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function deleteBus(bus) {
            delete bus["_id"];
            BusResource.buses(token).delete({
                tenantId: $scope.tenantId,
                busId: bus.id}, bus).$promise.then(function(data){
                    console.log(data);
                }, function(error){
            });
            var index = 0;

            while (index < $scope.buses.length && bus.id != $scope.buses[index].id) {
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