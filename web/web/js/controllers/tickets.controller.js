/**
 * Created by Lucia on 6/1/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('TicketsController', TicketsController);
    TicketsController.$inject = ['$scope', '$rootScope'];
    /* @ngInject */
    function TicketsController($scope, $rootScope) {
        $rootScope.show = true;
        $scope.toggle = toggle;

        $scope.journies = [{
            name : "Planificar Viajes"
        } , {
            name: "Administrar Usuarios"
        } , {
            name: "Administrar Sucursales"
        } , {
            name: "Administrar Cajas"
        } , {
            name: "Administrar Unidades"
        } , {
            name: "Administrar Paradas"
        } , {
            name: "Administrar Trayectos"
        } , {
            name: "Administrar Servicios"
        } , {
            name: "Personalizar Estilos"
        }];

        $scope.selected = [1];

        function toggle(item, list) {
            var idx = list.indexOf(item);
            if (idx > -1) {
                list.splice(idx, 1);
            }
            else {
                list.push(item);
            }
        };

    }
})();