/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('RouteController', RouteController);
    RouteController.$inject = ['$scope', '$mdDialog', 'RouteResource'];
    /* @ngInject */
    function RouteController($scope, $mdDialog, RouteResource) {
        $scope.showRoutes = showRoutes;
        $scope.createRoute = createRoute;
        $scope.deleteRoute = deleteRoute;

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.routes = [{
            'name': '1'
        }, {
            'name': '2'
        }];

        if ($scope.routes.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }


        /*RouteResource.query({
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            var journeys = $scope.journeys.concat(result);
            $scope.journeys = journeys;
        });
        */


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

        function deleteRoute(text) {
            //TODO: ver si aca va el id, el name o quien (supongo que va el id nomas);
            /*
            bus.active = false;
            RouteResource.update({id: bus.id, tenantId: $scope.tenantId}, bus).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
                console.log(style);
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
            */
            var index = 0;

            while (index < $scope.routes.length && text != $scope.routes[index].name) {
                index++;
            }

            if (index < $scope.routes.length) {
                $scope.routes.splice(index, 1);
            }

            if ($scope.routes.length === 0) {
                $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
            }

        }

    }
})();