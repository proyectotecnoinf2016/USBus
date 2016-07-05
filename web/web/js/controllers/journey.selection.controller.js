/**
 * Created by Lucia on 6/1/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('TicketsController', TicketsController);
    TicketsController.$inject = ['$scope', '$mdDialog', 'JourneyResource', 'localStorage', '$rootScope', '$location', 'dayOfWeek'];
    /* @ngInject */
    function TicketsController($scope, $mdDialog, JourneyResource, localStorage, $rootScope, $location, dayOfWeek ) {
        $scope.showTicket = showTicket;
        $scope.getJourneys = getJourneys;

        $scope.tenantId = 0;



        $scope.from = '';
        $scope.to = '';
        $scope.date = '';
        $scope.showJourneys = true;

        $rootScope.$emit('options', 'tickets');

        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        function getJourneys(from, to, date) {
            $scope.formattedDate = moment(date).format('MM/DD/YYYY');
            //query=DATE_ORIGIN_DESTINATION&date=08/02/2016&origin=Montevideo&destination=Colonia&offset=0&limit=100&status=ACTIVE&active=true

            JourneyResource.journeys(token).query({
                offset: 0,
                limit: 100,
                tenantId: $scope.tenantId,
                origin: from,
                destination: to,
                date: $scope.formattedDate,
                status: 'ACTIVE',
                query: 'DATE_ORIGIN_DESTINATION'
            }).$promise.then(function(result) {
                console.log(result);
                //var journeys = $scope.journeys.concat(result);
                $scope.journeys = [];
                var i = 0;
                for (i = 0; i < result.length; i ++) {
                    result[i].day = dayOfWeek.getDay(result[i].service.day);
                    result[i].time = moment(result[i].service.time).format('HH:mm');
                }
                $scope.journeys = result;
                console.log($scope.journeys);
            });
        }




        function showTicket(item, ev) {
            $scope.showJourneys = false;
            /*$mdDialog.show({
                controller : 'CreateTicketController',
                templateUrl : 'templates/ticket.create.html',
                locals:{journey: item}, 
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : false
            }).then(
                function(answer) {
                    $scope.status = 'You said the information was "'
                        + answer + '".';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });*/
        };

    }
})();