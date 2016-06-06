/**
 * Created by Lucia on 6/4/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('CreateTicketController', CreateTicketController);
    CreateTicketController.$inject = ['$scope', '$mdDialog', 'journeyId'];
    /* @ngInject */
    function CreateTicketController($scope, $mdDialog, journeyId) {
        //GENERAL VARIABLES
        $scope.tobedone = journeyId;
        $scope.max = 1;
        $scope.selectedIndex = 0;

        //SEATS VARIABLES
        $scope.selected = [];
        $scope.seats = [];
        $scope.firstRow = [];
        $scope.secondRow = [];
        $scope.thirdRow = [];
        $scope.fourthRow = [];
        $scope.soldSeats = [5, 2, 19];

        //FUNCTIONS
        $scope.selectedSeat = selectedSeat;
        $scope.exists = exists;
        $scope.soldSeat = soldSeat;
        $scope.cancel = cancel;
        $scope.nextTab = nextTab;

        var s = 44;
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