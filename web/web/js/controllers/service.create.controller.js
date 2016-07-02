/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateServiceController', CreateServiceController);
    CreateServiceController.$inject = ['$scope', 'localStorage', 'ServiceResource', 'RouteResource',
                                       '$mdDialog', 'theme', '$mdpDatePicker', '$mdpTimePicker'];
    /* @ngInject */
    function CreateServiceController($scope, localStorage, ServiceResource, RouteResource, $mdDialog, theme,
                                        $mdpDatePicker, $mdpTimePicker) {
        $scope.createService = createService;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.toggle = toggle;
        $scope.exists = exists;
        $scope.showDatePicker = showDatePicker;
        $scope.showTimePicker = showTimePicker;

        $scope.currentDate = new Date();
        function showDatePicker(ev) {
            $mdpDatePicker($scope.currentDate, {
                targetEvent: ev
            }).then(function(selectedDate) {
                $scope.currentDate = selectedDate;
            });;
        };

        function showTimePicker(ev) {
            $mdpTimePicker($scope.currentTime, {
                targetEvent: ev
            }).then(function(selectedDate) {
                $scope.currentTime = selectedDate;
            });;
        }


        $scope.selectedDays = [];
        $scope.routes = [];
        $scope.selectedRoute = '';
        $scope.theme = theme;
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
        /*
        RouteResource.routes(token).query({
            offset: 0,
            limit: 100,
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            $scope.routes = result;

        });*/
        

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
            item.active = true;
            item.day = $scope.selectedDays;
            item.time = [];
            $scope.hour = moment($scope.hour).format('YYYY-MM-DDTHH:mm:ss.000Z');
            item.time.push($scope.hour);


            //2012-10-01T09:45:00.000+02:00

            console.log(item);

            ServiceResource.services(token).save({tenantId: $scope.tenantId },item, function(){
                showAlert('Exito!', 'Se ha creado el servicio de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error');
            } );


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
