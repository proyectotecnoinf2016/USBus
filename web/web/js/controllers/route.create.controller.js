/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateRouteController', CreateRouteController);
    CreateRouteController.$inject = ['$scope', 'localStorage', 'RouteResource', '$mdDialog', 'BusStopResource'];
    /* @ngInject */
    function CreateRouteController($scope, localStorage, RouteResource, $mdDialog, BusStopResource) {
        $scope.createRoute = createRoute;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.nextTab = nextTab;
        $scope.deleteRouteStop = deleteRouteStop;
        $scope.addRouteStop = addRouteStop;
        $scope.compare = compare;
        $scope.addRouteStopsToArray = addRouteStopsToArray;
        $scope.queryOrigin = queryOrigin;
        $scope.queryDestination = queryDestination;

        $scope.routeStops = [];
        $scope.busStops = [{"name": "Montevideo", "id": 1, "active": true, "tenantId": 3}, {"name": "Colonia", "id": 2, "active": true, "tenantId": 3}];
        $scope.selectedIndex = 0;
        $scope.origin = [];

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }


        function createRoute(item) {
            item.active = true;
            item.tenantId = $scope.tenantId;
            item.busStops = $scope.routeStops.sort(compare);
            item.origin.active = true;
            item.destination.active = true;
            item.origin.stopTime = 10;
            item.destination.stopTime = 10;
            console.log(item);
            RouteResource.routes(token).save({
                tenantId: $scope.tenantId

            }, item,function (resp) {
                console.log(resp);
                showAlert('Exito!', 'Se ha creado su ruta de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al crear la Ruta');
            } );
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

        function addRouteStop() {
            $scope.routeStops.sort(compare);
            $scope.routeStops.push({});

        }

        function deleteRouteStop(item) {
            var index = 0;

            while (index < $scope.routeStops.length && item.name != $scope.routeStops[index].name) {
                index++;
            }

            if (index < $scope.routeStops.length) {
                $scope.routeStops.splice(index, 1);
            }


        }

        function cancel() {
            $mdDialog.cancel();
        };

        function addRouteStopsToArray(route) {
            if ($scope.routeStops != null && $scope.routeStops.length == 0) {
                if (route.origin.name != null && route.origin.name != '') {
                    $scope.routeStops.push({name: route.origin.name});
                }
                if (route.destination.name != null && route.destination.name != '') {
                    $scope.routeStops.push({name: route.destination.name});
                }
            }
        }

        function nextTab(route) {
            addRouteStopsToArray(route);
            var index = ($scope.selectedIndex == $scope.max) ? 0 : $scope.selectedIndex + 1;
            $scope.selectedIndex = index;
        }

        function compare(a,b) {
            if (a.km < b.km)
                return -1;
            if (a.km > b.km)
                return 1;
            return 0;
        }

        function queryDestination(origin) {
            return BusStopResource.busStops(token).query({
                offset: 0,
                limit: 5,
                tenantId: $scope.tenantId,
                origin : origin
            }).$promise.then(function(result) {
                console.log(result);
                $scope.busStops = result;

            });
        }

        function queryOrigin(name) {
            return $scope.busStops;
            return BusStopResource.busStops(token).query({
                offset: 0,
                limit: 5,
                tenantId: $scope.tenantId,
                name : name
            }).$promise.then(function(result) {
                console.log(result);
                $scope.busStops = result;

            });

        }


        function getGeneric() {
            return $scope.busStops;
        }
    }
})();
