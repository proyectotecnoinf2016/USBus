/**
 * Created by Lucia on 6/1/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('JourneyController', JourneyController);
    JourneyController.$inject = ['$scope', '$mdDialog', 'JourneyResource', 'localStorage', '$rootScope'];
    /* @ngInject */
    function JourneyController($scope, $mdDialog, JourneyResource, localStorage, $rootScope) {
        $scope.tenantId = 0;
        $scope.showTicket = showTicket;

        $rootScope.$emit('options', 'admin');

        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        /*JourneyResource.journeys(token).query({
            offset: 0,
            limit: 100,
            tenantId: $scope.tenantId
        }).$promise.then(function(result) {
            $scope.journeys = result;
        });
*/


        function showTicket(item, ev) {
            $mdDialog.show({
                controller : 'CreateTicketController',
                templateUrl : 'templates/ticket.create.html',
                locals:{journey: item}, //text va a ser usado para pasar el id del journey
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