/**
 * Created by Lucia on 6/1/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('TicketsController', TicketsController);
    TicketsController.$inject = ['$scope', '$mdDialog', 'JourneyResource', 'localStorage', '$rootScope', '$location', 'dayOfWeek', 'TicketResource'];
    /* @ngInject */
    function TicketsController($scope, $mdDialog, JourneyResource, localStorage, $rootScope, $location, dayOfWeek, TicketResource ) {
        //FUNCTIONS
        $scope.selectedSeat = selectedSeat;
        $scope.exists = exists;
        $scope.soldSeat = soldSeat;
        $scope.cancel = cancel;
        $scope.nextTab = nextTab;
        $scope.sell = sell;
        $scope.showTicket = showTicket;
        $scope.getJourneys = getJourneys;

        $scope.tenantId = 0;
        $scope.journeyNotSelected = true;

        //SEATS VARIABLES
        $scope.selected = [];
        $scope.seats = [];
        $scope.firstRow = [];
        $scope.secondRow = [];
        $scope.thirdRow = [];
        $scope.fourthRow = [];
        $scope.soldSeats = [];


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




        function showTicket(journey, ev) {
            $scope.journeyNotSelected = false;
            console.log(journey);
            nextTab();
            $scope.showJourneys = false;
            $scope.journey = journey;
            if (journey.seatsState != null) {
                var i = 0;
                for (i = 0; i < journey.seatsState.length; i++) {
                    $scope.soldSeats.push(journey.seatsState[i].number);
                }
            }
            else {
                journey.seatsState = [];
            }

            var i = 1;

            var seatsCount = journey.seats;

            while (i <= seatsCount) {
                if (i <= seatsCount) {
                    $scope.firstRow.push(i);
                    i++;
                }
                if (i <= seatsCount) {
                    $scope.secondRow.push(i);
                    i++;
                }
                if (i <= seatsCount) {
                    $scope.thirdRow.push(i);
                    i++;
                }
                if (i <= seatsCount) {
                    $scope.fourthRow.push(i);
                    i++;
                }
            }

        };

        function selectedSeat(item) {
            var i = 0;
            var exists = false;
            var existsIndex = 0;

            while (i < $scope.selected.length) {
                if ($scope.selected[i] == item) {
                    exists = true;
                    existsIndex = i;
                }
                i++;
            }

            if (exists) {
                $scope.selected.splice(existsIndex, 1);
            }
            else {
                $scope.selected.push(item);
            }
        }

        function soldSeat(item, list) {
            return list.indexOf(item) > -1;
        }

        function sell() {
            var journey = $scope.journey;
            var ticket = [];
            console.log($scope.journey);
            $scope.userName = 0;
            if (typeof localStorage.getData('userName') !== 'undefined' && localStorage.getData('userName') != null) {
                $scope.userName = localStorage.getData('userName');
            }

            var token = null;//localStorage.getData('token');
            if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
                token = localStorage.getData('token');
            }

            ticket.tenantId = $scope.tenantId;
            ticket.amount = 0;
            ticket.passengerName = '';
            ticket.sellerName = $scope.userName;
            ticket.closed = true;
            ticket.status = 'CONFIRMED';
            ticket.journeyId = $scope.journey.Id;
            ticket.seat = $scope.selected[0];
            ticket.getOnStopName = $scope.getOnStopName;
            ticket.getOffStopName = $scope.getOffStopName;

            ticket.emissionDate = new Date();//2016-07-06T01:50:45.077Z
            console.log('ticket');
            console.log(ticket);
            TicketResource.tickets(token).save({
                tenantId: $scope.tenantId

            }, ticket,function (resp) {
                console.log(resp);
                alert('bien ahi');
            }, function (error) {
                console.log(error);
            } );



            /*

             private Double amount;
             private String passengerName;
             private String sellerName;
             private Boolean closed;
             private TicketStatus status;
             private Long journeyId;
             private Integer seat;
             private String getOnStopName;
             private String getOffStopName;

             */
        }

        function exists(item, list) {
            return list.indexOf(item) > -1;
        };

        function cancel() {
            $scope.selectedIndex = 0;
        };

        function nextTab() {
            var index = ($scope.selectedIndex == $scope.max) ? 0 : $scope.selectedIndex + 1;
            $scope.selectedIndex = index;
        }

    }
})();