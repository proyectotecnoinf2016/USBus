/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('BusController', BusController);
    BusController.$inject = ['$scope', '$mdDialog', 'BusResource'];
    /* @ngInject */
    function BusController($scope, $mdDialog, BusResource) {
        $scope.showBus = showBus;
        $scope.tenantId = 0;
        $scope.buses = [{
            'name': '1'
        }, {
            'name': '2'
        }];

        /*BusResource.query({
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            console.log(result);
            var journeys = $scope.journeys.concat(result);
            $scope.journeys = journeys;
        });
        */


        function showBus(text, ev) {
            $mdDialog.show({
                controller : 'EditBusController',
                templateUrl : 'templates/bus.edit.html',
                locals:{busId: text}, //text va a ser usado para pasar el id del journey
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
    }
})();