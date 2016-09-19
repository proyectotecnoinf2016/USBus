/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('ParcelController', ParcelController);
    ParcelController.$inject = ['$scope', '$mdDialog', 'ParcelResource','JourneyResource','BusStopResource', 'localStorage', '$rootScope'];
    /* @ngInject */
    function ParcelController($scope, $mdDialog, ParcelResource,JourneyResource,BusStopResource, localStorage, $rootScope) {
        var vm = $scope;

        vm.createParcel = createParcel;
        vm.updateParcel = updateParcel;
        vm.getParcels = getParcels;
        vm.getJourneys = getJourneys;
        vm.queryBusStops = queryBusStops;

        vm.message = '';
        vm.tenantId = 0;
        vm.parcels = [];

        $rootScope.$emit('options', 'tickets');

        vm.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            vm.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }
        var now = new Date();
        var nowFormatted = moment(now).format('MM/DD/YYYY');

        // ParcelResource.parcels(token).query({
        //     query:"ENTERED",
        //     date: nowFormatted,
        //     offset: 0,
        //     limit: 10,
        //     tenantId: vm.tenantId
        // }).$promise.then(function(result) {
        //     console.log(result);
        //     vm.parcels = result;
        //
        // });

        if (vm.parcels.length === 0) {

            vm.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }

        function getJourneys(from, to, date) {
            vm.formattedDate = moment(date).format('MM/DD/YYYY');
            vm.originName = origin.name;
            vm.destinationName = destination.name;
            JourneyResource.journeys(token).query({
                offset: 0,
                limit: 100,
                tenantId: vm.tenantId,
                origin: vm.fromName,
                destination: vm.toName,
                date: vm.formattedDate,
                status: 'ACTIVE',
                query: 'DATE_ORIGIN_DESTINATION'
            }).$promise.then(function(result) {
                console.log(result);
                //var journeys = $scope.journeys.concat(result);
                vm.journeys = [];
                var i = 0;
                for (i = 0; i < result.length; i ++) {
                    result[i].day = dayOfWeek.getDay(result[i].service.day);
                    result[i].time = moment(result[i].service.time).format('HH:mm');
                }
                vm.journeys = result;
                console.log(vm.journeys);
            });
        }
        
        function createParcel(ev) {
            $mdDialog.show({
                controller : 'CreateParcelController',
                templateUrl : 'templates/bus.create.html',
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : false,
                locals : {theme: vm.theme}
            }).then(
                function() {
                    vm.parcels = ParcelResource.parcels(token).query({
                        query:"ALL",
                        status:true,
                        offset: 0,
                        limit: 100,
                        busStatus: 'ACTIVE',
                        tenantId: vm.tenantId
                    });
                }, function() {
                    vm.status = 'You cancelled the dialog.';
                });
        };

        function updateParcel(bus) {
            delete bus["_id"];
            ParcelResource.parcels(token).delete({
                tenantId: vm.tenantId,
                busId: bus.id}, bus).$promise.then(function(data){
                    console.log(data);
                }, function(error){
            });
            var index = 0;

            while (index < vm.parcels.length && bus.id != vm.parcels[index].id) {
                index++;
            }

            if (index < vm.parcels.length) {
                vm.parcels.splice(index, 1);
            }

            if (vm.parcels.length === 0) {
                vm.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
            }

        }

        function getParcels(origin, destination, enteredDate, shippedDate) {
            alert("GOLA");
            if ($isEmpty(enteredDate)){
                console.log("EnTRE EN EL ISEMPTY")

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
        function queryBusStops(name) {
            return BusStopResource.busStops(token).query({
                query:"ALL",
                status:true,
                offset: 0,
                limit: 5,
                tenantId: $scope.tenantId,
                name : name
            }).$promise;

        }
    }
})();