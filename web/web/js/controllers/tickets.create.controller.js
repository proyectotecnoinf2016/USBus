/**
 * Created by Lucia on 6/4/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('CreateTicketController', CreateTicketController);
    CreateTicketController.$inject = ['$scope', '$mdDialog', 'journey', 'localStorage', 'TicketResource'];
    /* @ngInject */
    function CreateTicketController($scope, $mdDialog, journey, localStorage, TicketResource) {
        //GENERAL VARIABLES
        $scope.journey = journey;
        $scope.max = 1;
        $scope.selectedIndex = 0;


        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        //SEATS VARIABLES
        $scope.selected = [];
        $scope.seats = [];
        $scope.firstRow = [];
        $scope.secondRow = [];
        $scope.thirdRow = [];
        $scope.fourthRow = [];
        $scope.soldSeats = [];

        if (journey.seatsState != null) {
            var i = 0;
            for (i = 0; i < journey.seatsState.length; i++) {
                $scope.soldSeats.push(journey.seatsState[i].number);
            }
        }


        //FUNCTIONS
        $scope.selectedSeat = selectedSeat;
        $scope.exists = exists;
        $scope.soldSeat = soldSeat;
        $scope.cancel = cancel;
        $scope.nextTab = nextTab;
        $scope.sell = sell;

        var s = 40;
        var i = 1;

        var seatsCount = s;

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

        function sell(ticket) {

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
            $mdDialog.cancel();
        };

        function nextTab() {
            var index = ($scope.selectedIndex == $scope.max) ? 0 : $scope.selectedIndex + 1;
            $scope.selectedIndex = index;
        }

    }
})();