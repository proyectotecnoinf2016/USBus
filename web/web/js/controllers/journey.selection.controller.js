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
        $scope.calculatePrice = calculatePrice;
        $scope.showAlert = showAlert;

        $scope.tenantId = 0;
        $scope.price = 0;
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
            var sold = false;

            var j = 0;
            while  (j < $scope.soldSeats.length && $scope.soldSeats[j] != item) {
                j++;
            }

            if (j == $scope.soldSeats.length && $scope.soldSeats[j] != item) {
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

        }

        function soldSeat(item, list) {
            return list.indexOf(item) > -1;
        }


        function calculatePrice() {
            if ($scope.ticket != null && $scope.ticket != 'undefined' &&
                $scope.ticket.getOnStopName != null && $scope.ticket.getOffStopName != null) {
                JourneyResource.journeys(token).get({
                    price: 'price',
                    tenantId: $scope.tenantId,
                    origin: $scope.ticket.getOnStopName,
                    destination: $scope.ticket.getOffStopName,
                    journeyId: $scope.journey.id
                }).$promise.then(function(result) {
                    console.log(result);
                    $scope.price = result.price;

                });
            }
        }


        function sell() {

            var journey = $scope.journey;
            console.log($scope.journey);
            $scope.userName = 0;
            if (typeof localStorage.getData('userName') !== 'undefined' && localStorage.getData('userName') != null) {
                $scope.userName = localStorage.getData('userName');
            }

            var token = null;//localStorage.getData('token');
            if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
                token = localStorage.getData('token');
            }

            $scope.ticket.combination = null;
            $scope.ticket.combinationId = null;

            if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
                $scope.ticket.sellerName = localStorage.getData('userName');
            }

            $scope.ticket.closed = true;


            if (localStorage.getData('branchId') != null && localStorage.getData('branchId') != '') {
                $scope.ticket.branchId = localStorage.getData('branchId');
            }

            if (localStorage.getData('windowsId') != null && localStorage.getData('windowsId') != '') {
                $scope.ticket.windowId = localStorage.getData('windowsId');
            }

            /*
             newTicket.put("amount", paymentAmount);
             newTicket.put("branchId", 0);
             newTicket.put("windowId", 0);
             */

            $scope.ticket.tenantId = $scope.tenantId;
            $scope.ticket.amount = $scope.price;
            $scope.ticket.passengerName = '';
            $scope.ticket.sellerName = $scope.userName;
            $scope.ticket.closed = true;
            $scope.ticket.status = 'CONFIRMED';
            $scope.ticket.journeyId = $scope.journey.id;

            if ($scope.journey != null && $scope.journey != 'undefined' &&
                $scope.journey.service != null && $scope.journey.service != 'undefined' &&
                $scope.journey.service.route != null && $scope.journey.service.route != 'undefined') {
                $scope.ticket.routeId = $scope.journey.service.route.id;
                $scope.ticket.hasCombination = $scope.journey.service.route.hasCombination;
            }



            $scope.ticket.emissionDate = new Date();//2016-07-06T01:50:45.077Z
            console.log('ticket');
            console.log($scope.ticket);

            var i = 0;
            for (i = 0; i < $scope.selected.length; i++) {
                $scope.ticket.seat = $scope.selected[i];
                TicketResource.tickets(token).save({
                    tenantId: $scope.tenantId

                }, $scope.ticket,function (resp) {
                    console.log(resp);
                    showAlert('Exito!', 'Se ha realizado la venta de forma exitosa');
                    cancel();
                }, function (error) {
                    console.log(error);
                    showAlert('Error!', 'Ocurrio un erro al realizar la venta');
                } );
            }
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

        function exists(item, list) {
            return list.indexOf(item) > -1;
        };

        function cancel() {
            $scope.selectedIndex = 0;
            $scope.selected = [];
            $scope.seats = [];
            $scope.firstRow = [];
            $scope.secondRow = [];
            $scope.thirdRow = [];
            $scope.fourthRow = [];
            $scope.soldSeats = [];
            $scope.journeys = [];

            $scope.from = '';
            $scope.to = '';
            $scope.date = '';
            $scope.showJourneys = true;
        };

        function nextTab() {
            if ($scope.selectedIndex == 2) {
                calculatePrice();
            }

            var index = ($scope.selectedIndex == $scope.max) ? 0 : $scope.selectedIndex + 1;
            $scope.selectedIndex = index;
        }

    }
})();