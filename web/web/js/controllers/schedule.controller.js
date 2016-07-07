/**
 * Created by JPMartinez on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('ScheduleController', ScheduleController);
    ScheduleController.$inject = ['$scope', '$mdDialog', 'JourneyResource', 'localStorage', '$rootScope', '$timeout'];
    /* @ngInject */
    function ScheduleController($scope, $mdDialog, JourneyResource, localStorage, $rootScope, $timeout) {

        $rootScope.$emit('options', 'admin');
        var vm = this;
        var token = null;
        init();
        vm.showAlert = showAlert;


        /******************************************************************/
        /*************************** FUNCIONES ****************************/
        /******************************************************************/

        function init() {
            vm.message = '';
            vm.journeys = [];
            vm.tenantId = localStorage.getData('tenantId');
            token = localStorage.getData('token');
            vm.username = localStorage.getData('userName');
            getSchedule();
        }

        function showAlert(title, content) {
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

        function getSchedule() {
            // JourneyResource.journeys(token).queryOne({
            //     query: "USERNAME",
            //     username: vm.username,
            //     offset: 0,
            //     limit: 100,
            //     tenantId: vm.tenantId
            // }).$promise.then(function (result) {
            //     console.log(result);
            //     vm.journeys = result;
            //
            // });
            JourneyResource.journeys(token).queryOne({
                query: "USERNAME",
                username: vm.username,
                offset: 0,
                limit: 100,
                tenantId: vm.tenantId
            },function (result) {
                console.log(result);
                vm.journeys = result;

            });
        };

    }
})();
