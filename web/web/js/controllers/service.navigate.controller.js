/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('ServiceController', ServiceController);
    ServiceController.$inject = ['$scope', '$mdDialog', 'ServiceResource'];
    /* @ngInject */
    function ServiceController($scope, $mdDialog, ServiceResource) {
        $scope.showServices = showServices;
        $scope.createService = createService;
        $scope.deleteService = deleteService;

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.services = [{
            'name': '1'
        }, {
            'name': '2'
        }];

        if ($scope.services.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }


        /*ServiceResource.query({
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            var journeys = $scope.journeys.concat(result);
            $scope.journeys = journeys;
        });
        */


        function showServices(item, ev) {
            $mdDialog.show({
                controller : 'EditServiceController',
                templateUrl : 'templates/service.edit.html',
                locals:{serviceToEdit: item},
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

        function createService(ev) {
            $mdDialog.show({
                controller : 'CreateServiceController',
                templateUrl : 'templates/service.create.html',
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

        function deleteService(text) {
            //TODO: ver si aca va el id, el name o quien (supongo que va el id nomas);
            /*
            bus.active = false;
            ServiceResource.update({id: bus.id, tenantId: $scope.tenantId}, bus).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
                console.log(style);
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
            */
            var index = 0;

            while (index < $scope.services.length && text != $scope.services[index].name) {
                index++;
            }

            if (index < $scope.services.length) {
                $scope.services.splice(index, 1);
            }

            if ($scope.services.length === 0) {
                $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
            }

        }

    }
})();