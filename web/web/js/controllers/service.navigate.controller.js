/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('ServiceController', ServiceController);
    ServiceController.$inject = ['$scope', '$mdDialog', 'ServiceResource', 'localStorage', '$rootScope', 'dayOfWeek'];
    /* @ngInject */
    function ServiceController($scope, $mdDialog, ServiceResource, localStorage, $rootScope, dayOfWeek) {
        $scope.showServices = showServices;
        $scope.createService = createService;
        $scope.deleteService = deleteService;
        $scope.showAlert = showAlert;

        $scope.message = '';
        $scope.services = [];
        $scope.tenantId = 0;

        $rootScope.$emit('options', 'admin');

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }
        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }


        ServiceResource.services(token).query({
            offset: 0,
            limit: 100,
            tenantId: $scope.tenantId,
            query: 'ALL'
        }).$promise.then(function(result) {
            console.log(result);

            var i = 0;
            for (i = 0; i < result.length; i++) {
                var service = result[i];
                service.day = dayOfWeek.getDay(service.day);

                service.time = moment(service.time).format('HH:mm');

                $scope.services.push(service);
            }
        });

        if ($scope.services.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }


        function showServices(item, ev) {
            $mdDialog.show({
                controller : 'EditServiceController',
                templateUrl : 'templates/service.edit.html',
                locals:{serviceToEdit: item, theme : $scope.theme},
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : false
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
                clickOutsideToClose : false,
                theme : $scope.theme
            }).then(
                function(answer) {
                    $scope.status = 'Aca deberia hacer la query de nuevo';
                    $scope.services = ServiceResource.services(token).query({
                        offset: 0,
                        limit: 100,
                        tenantId: $scope.tenantId,
                        query: "ALL"
                    });
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

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

        function deleteService(item) {
            delete item["_id"];

            ServiceResource.services(token).delete({
                serviceId: item.id,
                tenantId: $scope.tenantId}, item).$promise.then(function(data){
                showAlert('Exito!','Se ha eliminado el elemento de forma exitosa');
                console.log(service);
            }, function(error){
                showAlert('Error!','Ocurrió un error al procesar su petición');
            });
            var index = 0;

            while (index < $scope.services.length && item.id != $scope.services[index].id) {
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