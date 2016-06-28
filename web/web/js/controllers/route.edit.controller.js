/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditRouteController', EditRouteController);
    EditRouteController.$inject = ['$scope', 'RouteResource', '$mdDialog', 'routeToEdit', 'localStorage', 'theme'];
    /* @ngInject */
    function EditRouteController($scope, RouteResource, $mdDialog, routeToEdit, localStorage, theme) {
        $scope.route = routeToEdit;

        $scope.theme = theme;
        

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        console.log(routeToEdit);
        

        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.addRouteStop = addRouteStop;
        $scope.deleteRouteStop = deleteRouteStop;
        $scope.updateRoute = updateRoute;
        $scope.nextTab = nextTab;
        $scope.compare = compare;

/*
        $scope.route = RouteResource.get({
            id: $scope.routeId,
            tenantId: $scope.tenantId
        });
*/

        function addRouteStop() {

            $scope.route.busStops.sort(compare);
            $scope.route.busStops.push({combinationPoint: false});

        }

        function deleteRouteStop(item) {
            var index = 0;

            while (index < $scope.route.busStops.length && item.name != $scope.route.busStops[index].name) {
                index++;
            }

            if (index < $scope.route.busStops.length) {
                $scope.route.busStops.splice(index, 1);
            }


        }

        function nextTab() {
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

        function updateRoute(item) {
            delete item["_id"];

            RouteResource.routes(token).update({
                tenantId: $scope.tenantId,
                routeId: item.id
            }, item,function (resp) {
                console.log(resp);
                showAlert('Exito!', 'Se ha editado su Ruta de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al editar la Ruta');
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

        function cancel() {
            $mdDialog.cancel();
        };


    }
})();
