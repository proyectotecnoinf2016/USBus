/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateJourneyController', CreateJourneyController);
    CreateJourneyController.$inject = ['$scope', 'localStorage', 'JourneyResource', 'ServiceResource', 'BusResource', 'HumanResource', '$mdDialog', 'theme', 'date'];
    /* @ngInject */
    function CreateJourneyController($scope, localStorage, JourneyResource, ServiceResource, BusResource, HumanResource, $mdDialog, theme, date) {
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.createJourney = createJourney;
        $scope.queryService = queryService;
        $scope.queryBus = queryBus;
        $scope.queryResource = queryResource;

        $scope.theme = theme;
        $scope.date = date;
        $scope.buses = [];
        $scope.users = [];


        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        function queryService(name) {
            $scope.dayOfWeek = moment($scope.date).format('dddd').toUpperCase();
            console.log($scope.dayOfWeek);
            var lista = ServiceResource.services(token).query({
                offset: 0,
                limit: 100,
                tenantId: $scope.tenantId,
                query: 'DAYOFWEEK',
                dayOfWeek: $scope.dayOfWeek

            }).$promise;
            console.log(lista);

            

            return lista;
        }


        function queryBus(busName) {

            return BusResource.buses(token).query({
                query:"BUSSTATUS",
                status:true,
                busStatus: "ACTIVE",
                offset: 0,
                limit: 5,
                tenantId: $scope.tenantId
            }).$promise;
            return [];
        }


        function queryResource(hrEmail) {
            return HumanResource.resources(token).query({
                query:"ALL",
                status:true,
                email: hrEmail,
                offset: 0,
                limit: 5,
                tenantId: $scope.tenantId
            }).$promise;
            return [];
        }


        function createJourney(item) {
            item.tenantId = $scope.tenantId;
            item.date = moment($scope.date).format('YYYY-MM-DDTHH:mm:ss.000Z');
            //item.service = $scope.service.id;
            //item.bus = $scope.bus.id;
            item.seats = item.bus.seats;
            item.standingPassengers = item.bus.standingPassengers;
            item.trunkWeight = item.bus.trunkMaxWeight;
            item.status = 'ACTIVE';

            if (item.thirdPartyBus == null || item.thirdPartyBus == 'undefined') {
                item.thirdPartyBus = false;
            }

            console.log(item);

            JourneyResource.journeys(token).save({
                tenantId: $scope.tenantId

            }, item,function (resp) {
                console.log(resp);
                showAlert('Exito!', 'Se ha creado su viaje de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al crear el Viaje');
            } );

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
