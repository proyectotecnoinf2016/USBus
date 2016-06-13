/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateServiceController', CreateServiceController);
    CreateServiceController.$inject = ['$scope', 'localStorage', 'ServiceResource', '$mdDialog'];
    /* @ngInject */
    function CreateServiceController($scope, localStorage, ServiceResource, $mdDialog) {
        $scope.createService = createService;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;


        $scope.days = [{
            "name"  : "Lunes",
            "value" : "MONDAY"
        } , {
            "name"  : "Martes",
            "value" : "TUESDAY"
        } , {
            "name"  : "Miércoles",
            "value" : "WEDNESDAY"
        } , {
            "name"  : "Jueves",
            "value" : "THURSDAY"
        } , {
            "name"  : "Viernes",
            "value" : "FRIDAY"
        } , {
            "name"  : "Sábado",
            "value" : "SATURDAY"
        } , {
            "name"  : "Domingo",
            "value" : "SUNDAY"
        } , {
        }];

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }
        console.log($scope.tenantId);

        function createService(item) {
            item.tenantId = $scope.tenantId;
            ServiceResource.services(token).save({tenantId: $scope.tenantId },item, function(){
                showAlert('Exito!', 'Se ha creado su unidad de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al registrar el TENANT');
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
