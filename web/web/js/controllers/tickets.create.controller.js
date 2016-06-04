/**
 * Created by Lucia on 6/4/2016.
 */

(function () {
    'use strict';
    angular.module('usbus').controller('CreateTicketController', CreateTicketController);
    CreateTicketController.$inject = ['$scope', '$mdDialog', 'journeyId'];
    /* @ngInject */
    function CreateTicketController($scope, $mdDialog, journeyId) {
        $scope.tobedone = journeyId;
        $scope.seats = [];
        $scope.firstRow = [];
        $scope.secondRow = [];
        $scope.thirdRow = [];
        $scope.fourthRow = [];

        var s = 44;
        var i = 1;

        var seatsCount = s;

        if (s % 4 == 1) {
            //seatsCount = s - 5;
        }

        while (i <= seatsCount) {
            //7fd4f2

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

        /*if (s % 4 == 1) {
            $scope.firstRow.push(i);
            i++;
            $scope.secondRow.push(i);
            i++;
            $scope.thirdRow.push(i);
            i++;
            $scope.fourthRow.push(i);
            i++;
        }*/

        $scope.selected = [];

        $scope.selectedSeat = selectedSeat;
        $scope.exists = exists;

        function selectedSeat(item) {
            var i = 0;
            var exists = false;
            var existsIndex = 0;

            while ($scope.selected.length > 0 && i < $scope.selected.length - 1) {
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


        function exists(item, list) {
            return list.indexOf(item) > -1;
        };


    }
})();