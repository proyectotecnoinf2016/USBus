/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateRouteController', CreateRouteController);
    CreateRouteController.$inject = ['$scope', 'localStorage', 'RouteResource', '$mdDialog'];
    /* @ngInject */
    function CreateRouteController($scope, localStorage, RouteResource, $mdDialog) {
        $scope.createRoute = createRoute;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.nextTab = nextTab;

        $scope.selectedIndex = 0;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }


        function createRoute(item) {
            bus.status = 'ACTIVE';
            bus.active = true;
            bus.tenantId = $scope.tenantId;
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

        function cancel() {
            $mdDialog.cancel();
        };

        function nextTab() {
            var index = ($scope.selectedIndex == $scope.max) ? 0 : $scope.selectedIndex + 1;
            $scope.selectedIndex = index;
        }


    }
})();
