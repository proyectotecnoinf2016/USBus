/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('RouteController', RouteController);
    RouteController.$inject = ['$scope', '$mdDialog', 'RouteResource', '$rootScope', 'localStorage'];
    /* @ngInject */
    function RouteController($scope, $mdDialog, RouteResource, $rootScope, localStorage ) {
        $scope.showRoutes = showRoutes;
        $scope.createRoute = createRoute;
        $scope.deleteRoute = deleteRoute;
        $scope.showAlert = showAlert;

        $scope.routes = [];

        $scope.message = '';
        $scope.tenantId = 0;


        $rootScope.$emit('options', 'admin');




        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        RouteResource.routes(token).query({
            offset: 0,
            limit: 100,
            query: 'ALL',
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            $scope.routes = result;

        });
        if ($scope.routes.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }



        function showRoutes(item, ev) {
            $mdDialog.show({
                controller : 'EditRouteController',
                templateUrl : 'templates/route.edit.html',
                locals:{routeToEdit: item},
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

        function createRoute(ev) {
            $mdDialog.show({
                controller : 'CreateRouteController',
                templateUrl : 'templates/route.create.html',
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

        function deleteRoute(route) {
            delete route["_id"];
            route.active = false;
            RouteResource.routes(token).delete({
                    tenantId: $scope.tenantId,
                    routeId: route.id}, route).$promise.then(function(data){
                    var index = 0;

                    while (index < $scope.routes.length && route.name != $scope.routes[index].name) {
                        index++;
                    }

                    if (index < $scope.routes.length) {
                        $scope.routes.splice(index, 1);
                    }

                    if ($scope.routes.length === 0) {
                        $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
                    }
                    console.log(data);
                }, function(error){
                showAlert('Error!', error);
            });

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