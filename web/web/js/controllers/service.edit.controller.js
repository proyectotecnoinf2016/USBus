/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditServiceController', EditServiceController);
    EditServiceController.$inject = ['$scope', 'ServiceResource', '$mdDialog', 'serviceToEdit', 'localStorage', 'theme', 'dayOfWeek'];
    /* @ngInject */
    function EditServiceController($scope, ServiceResource, $mdDialog, serviceToEdit, localStorage, theme, dayOfWeek) {
        $scope.service = serviceToEdit;
        $scope.tenantId = 0;
        $scope.theme = theme;
        $scope.selectedDays = [];


        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.updateService = updateService;
        $scope.toggle = toggle;
        $scope.exists = exists;


        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');

        }
        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }
        console.log($scope.tenantId);


        function updateService(item) {
            console.log(item);
            delete item["_id"];
            item.time = moment(item.time).format('YYYY-MM-DDTHH:mm:ss.000Z');
            item.day = dayOfWeek.getDayOfWeek(item.day);

            ServiceResource.services(token).update({serviceId: item.id, tenantId: $scope.tenantId}, item).$promise.then(function(data){
                showAlert('Exito!','Se ha editado la Parada de forma exitosa');
                item.time = moment(item.time).format('HH:mm');
                item.day = dayOfWeek.getDay(item.day);

                console.log(item);
            }, function(error){
                showAlert('Error!','Ocurrió un error al procesar su petición');
            });

        }

        function toggle(item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list.push(item);
            }
        };


        function exists(item, list) {
            return list.indexOf(item) > -1;
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

        function cancel() {
            $mdDialog.cancel();
        };


    }
})();
