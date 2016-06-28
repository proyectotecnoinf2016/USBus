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
        $scope.tooltips = false;

        $scope.setDirection = setDirection;
        $scope.dayClick = dayClick;
        $scope.prevMonth = prevMonth;
        $scope.nextMonth = nextMonth;


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


        $scope.dayFormat = "d";

        // To select a single date, make sure the ngModel is not an array.
        $scope.selectedDate = null;

        var today = new Date();
        $scope.currentDay = today.getDate();
        $scope.currentMonth = today.getMonth()+1;
        $scope.currentYear = today.getFullYear();

        $scope.firstDayOfWeek = 0; // First day of the week, 0 for Sunday, 1 for Monday, etc.
        
        function setDirection(direction) {
            $scope.direction = direction;
            $scope.dayFormat = direction === "vertical" ? "EEEE, MMMM d" : "d";
        };

        function dayClick(date) {
            //$scope.msg = "You clicked " + $filter("date")(date, "MMM d, y h:mm:ss a Z");
            console.log(date);
        };

        function prevMonth(data) {
            $scope.msg = "You clicked (prev) month " + data.month + ", " + data.year;
        };

        function nextMonth(data) {
            $scope.msg = "You clicked (next) month " + data.month + ", " + data.year;
        };

            
        $scope.setDayContent = function(date) {

            // You would inject any HTML you wanted for
            // that particular date here.
            return "<p></p>";

            // You could also use an $http function directly.
            return $http.get("/some/external/api");

            // You could also use a promise.
            var deferred = $q.defer();
            $timeout(function() {
                deferred.resolve("<p></p>");
            }, 1000);
            return deferred.promise;

        };



    }
})();