/**
 * Created by JPMartinez on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CashRegisterController', CashRegisterController);
    CashRegisterController.$inject = ['$scope', '$mdDialog', 'CashRegisterResource', 'localStorage', '$rootScope'];
    /* @ngInject */
    function CashRegisterController($scope, $mdDialog, CashRegisterResource, localStorage, $rootScope) {
        $scope.isOpen = false;
        $scope.showAlert = showAlert;
        $scope.message = '';
        $scope.registrys = [];
        $rootScope.$emit('options', 'tickets');
        $scope.tenantId = 0;
        $scope.openCashRegister = openCashRegister;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }
        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }
        if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
            $scope.userName = localStorage.getData('userName');
        }
        $scope.branchId = localStorage.getData('branchId');
        $scope.windowsId = localStorage.getData('windowsId');

        $scope.isOpen = isOpen();


        /*
         CashRegisterResource.cashRegister(token).query({
         query:"ALL",
         status:true,
         offset: 0,
         limit: 100,
         tenantId: $scope.tenantId
         }).$promise.then(function(result) {
         console.log(result);
         $scope.busStops = result;

         });*/

        function isOpen() {
            alert("IsOPEN??")
            CashRegisterResource.cashRegister(token).query({
                query: "ISOPEN",
                branchId: $scope.branchId,
                windowsId: $scope.windowsId,
                tenantId: $scope.tenantId
            }).$promise.then(function (result) {
                console.log(result);
                return result[0].isOpen;

            });
        }

        function openCashRegister(ev) {
            alert("AAAAAAA");
            var cashRegister = {};
            cashRegister.tenantId = $scope.tenantId;
            cashRegister.branchId = $scope.branchId;
            cashRegister.windowsId = $scope.windowsId;
            cashRegister.sellerName = $scope.userName;
            cashRegister.amount = 0
            cashRegister.type = "CASH_INIT"

                // Appending dialog to document.body to cover sidenav in docs app
                var confirm = $mdDialog.prompt()
                    .title('Abrir caja.')
                    .textContent('Ingrese el monto incial.')
                    .placeholder('Monto Inicial')
                    .targetEvent(ev)
                    .ok('Aceptar')
                    .cancel('Cancelar');
                $mdDialog.show(confirm).then(function (result) {
                    cashRegister.amount = result;
                    CashRegisterResource.cashRegister(token).save({
                        tenantId: $scope.tenantId

                    }, cashRegister, function (resp) {
                        console.log(resp);
                        showAlert('Exito!', 'Caja abierta correctamente.');
                        $scope.isOpen = isOpen();
                        getCurrentCashRegistrys();
                    }, function (error) {
                        console.log(error);
                        showAlert('Error!', 'Ocurrió un error al abrir la caja.');
                    });
                }, function () {
                    $scope.status = 'You didn\'t name your dog.';
                });


        };

        function newEntry() {
            mdDialog.show({
                controller: 'CreateCashEntryController',
                templateUrl: 'templates/cashRegister.entry.create.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                locals: {theme: $scope.theme}
            }).then(
                function (answer) {
                    getCurrentCashRegistrys();
                }, function () {
                    console.log("Cancelaste el dialogo amigo.");
                });
        }

        function newWithdrawal() {
            mdDialog.show({
                controller: 'CreateCashWithdrawalController',
                templateUrl: 'templates/cashRegister.withdrawal.create.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                locals: {theme: $scope.theme}
            }).then(
                function (answer) {
                    getCurrentCashRegistrys();
                }, function () {
                    console.log("Cancelaste el dialogo amigo.");
                });
        }

        function cashCount() {
            CashRegisterResource.cashRegister(token).query({
                query: "CASH_COUNT",
                branchId: $scope.branchId,
                windowsId: $scope.windowsId,
                tenantId: $scope.tenantId
            }).$promise.then(function (result) {
                console.log(result);
                var cashCount = result[0].cashCount;
                showAlert('Arqueo de Caja', cashCount);
            });
        }

        function createBusStop(ev) {
            $mdDialog.show({
                controller: 'CreateBusStopController',
                templateUrl: 'templates/busStop.create.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                locals: {theme: $scope.theme}
            }).then(
                function (answer) {
                    $scope.status = 'Aca deberia hacer la query de nuevo';
                    BusStopResource.busStops(token).query({
                        query: "ALL",
                        status: true,
                        offset: 0,
                        limit: 100,
                        tenantId: $scope.tenantId
                    }).$promise.then(function (result) {
                        console.log(result);
                        $scope.busStops = result;

                    });
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

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
        function getCurrentCashRegistrys() {
            CashRegisterResource.cashRegister(token).query({
                query: "CURRENT",
                branchId: $scope.branchId,
                windowsId: $scope.windowsId,
                offset: 0,
                limit: 100,
                tenantId: $scope.tenantId
            }).$promise.then(function (result) {
                console.log(result);
                $scope.registrys = result;

            });
        };

    }
})();