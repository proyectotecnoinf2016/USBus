/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('ParcelController', ParcelController);
    ParcelController.$inject = ['$scope', 'localStorage', 'BusResource', '$mdDialog', '$rootScope', 'theme'];
    /* @ngInject */
    function ParcelController($scope, localStorage, BusResource, $mdDialog, $rootScope, theme) {
        $scope.createBus = createBus;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.theme = theme;
        
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }


        function createBus(bus) {
            bus.status = 'ACTIVE';
            bus.active = true;
            bus.tenantId = $scope.tenantId;

            BusResource.buses(token).save({
                tenantId: $scope.tenantId

            }, bus,function (resp) {
                console.log(resp);
                showAlert('Exito!', 'Se ha creado su unidad de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al registrar el Omnnibus');
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
