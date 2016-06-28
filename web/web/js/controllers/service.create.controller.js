/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateServiceController', CreateServiceController);
    CreateServiceController.$inject = ['$scope', 'localStorage', 'ServiceResource', 'RouteResource', '$mdDialog', 'theme'];
    /* @ngInject */
    function CreateServiceController($scope, localStorage, ServiceResource, RouteResource, $mdDialog, theme) {
        $scope.createService = createService;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.routes = [];

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
            item.active = true;


            delete item.route["id"];
            delete item.route["name"];
            delete item.route["active"];

            delete item.route["origin"];
            delete item.route["destination"];
            delete item.route["busStops"];
            delete item.route["hasCombination"];
            delete item.route["pricePerKm "];

            delete item.route["creationDate"];
            delete item.route["lastChange"];
            delete item.route["tenantId"];
            delete item.route["version"];

            console.log(item);

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
