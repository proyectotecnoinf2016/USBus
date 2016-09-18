/**
 * Created by JPMartinez on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CashRegisterController', CashRegisterController);
    CashRegisterController.$inject = ['$scope', '$mdDialog', 'CashRegisterResource', 'localStorage', '$rootScope','$timeout'];
    /* @ngInject */
    function CashRegisterController($scope, $mdDialog, CashRegisterResource, localStorage, $rootScope, $timeout) {

        $rootScope.$emit('options', 'tickets');
        var vm = this;
        var token = null;
        init();

        vm.openCashRegister = openCashRegister;
        vm.closeCashRegister = closeCashRegister;
        vm.cashCount = cashCount;
        vm.newEntry = newEntry;
        vm.newWithdrawal = newWithdrawal;
        vm.showAlert = showAlert;


        /*
         CashRegisterResource.cashRegister(token).query({
         query:"ALL",
         status:true,
         offset: 0,
         limit: 100,
         tenantId: vm.tenantId
         }).$promise.then(function(result) {
         console.log(result);
         vm.busStops = result;

         });*/



        /******************************************************************/
        /*************************** FUNCIONES ****************************/
        /******************************************************************/

        function init() {
            vm.message = '';
            vm.registrys = [];
            vm.tenantId = 0;
            // localStorage.setData("branchId", "1");
            // localStorage.setData("windowsId", "1");
            if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
                vm.tenantId = localStorage.getData('tenantId');
            }
            //localStorage.getData('token');
            if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
                token = localStorage.getData('token');
            }
            if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
                vm.userName = localStorage.getData('userName');
            }
            vm.branchId = localStorage.getData('branchId');
            vm.windowsId = localStorage.getData('windowsId');

            isOpen();
            if (vm.isOpen){
                getCurrentCashRegistrys();
            }
        }

        function isOpen() {
            CashRegisterResource.cashRegister(token).query({
                query: "ISOPEN",
                branchId: vm.branchId,
                windowsId: vm.windowsId,
                tenantId: vm.tenantId
            }).$promise.then(function (result) {
                $timeout(function(){
                    console.log(result[0].isOpen);
                    vm.isOpen = result[0].isOpen;
                    getCurrentCashRegistrys();
                },0);


            });
        }

        function closeCashRegister(ev){
            alert("closeCashRegister");
            var cashRegister = {};
            cashRegister.tenantId = vm.tenantId;
            cashRegister.branchId = vm.branchId;
            cashRegister.windowsId = vm.windowsId;
            cashRegister.sellerName = vm.userName;
            cashRegister.amount = 0
            cashRegister.type = "CASH_CLOSURE"

            // Appending dialog to document.body to cover sidenav in docs app
            var confirm = $mdDialog.prompt()
                .title('Cerrar caja.')
                .textContent('Ingrese el total actual de la caja.')
                .placeholder('Total')
                .targetEvent(ev)
                .ok('Aceptar')
                .cancel('Cancelar');
            $mdDialog.show(confirm).then(function (result) {
                cashRegister.amount = result;
                CashRegisterResource.cashRegister(token).save({
                    tenantId: vm.tenantId

                }, cashRegister, function (resp) {
                    console.log(resp);
                    showAlert('Exito!', 'La caja se cerró correctamente.');
                    isOpen();
                }, function (error) {
                    console.log(error);
                    showAlert('Error!', 'Ocurrió un error al cerrar la caja.');
                });
            }, function () {
            });
        };

        function openCashRegister(ev) {
            alert("openCashRegister");
            var cashRegister = {};
            cashRegister.tenantId = vm.tenantId;
            cashRegister.branchId = vm.branchId;
            cashRegister.windowsId = vm.windowsId;
            cashRegister.sellerName = vm.userName;
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
                    tenantId: vm.tenantId

                }, cashRegister, function (resp) {
                    console.log(resp);
                    showAlert('Exito!', 'Caja abierta correctamente.');
                    isOpen();
                    getCurrentCashRegistrys();
                }, function (error) {
                    console.log(error);
                    showAlert('Error!', 'Ocurrió un error al abrir la caja.');
                });
            }, function () {
            });


        };

        function newEntry(ev) {
            $mdDialog.show({
                controller: 'CreateCashEntryController',
                controllerAs:'vm',
                templateUrl: 'templates/cashRegister.entry.create.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                locals: {theme: vm.theme}
            }).then(
                function (answer) {
                    getCurrentCashRegistrys();
                }, function () {
                    console.log("Cancelaste el dialogo amigo.");
                });
        }

        function newWithdrawal(ev) {
            $mdDialog.show({
                controller: 'CreateCashWithdrawalController',
                controllerAs:'vm',
                templateUrl: 'templates/cashRegister.withdrawal.create.html',
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false,
                locals: {theme: vm.theme}
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
                branchId: vm.branchId,
                windowsId: vm.windowsId,
                tenantId: vm.tenantId
            }).$promise.then(function (result) {
                console.log(result);
                var cashCount = result[0].cashCount;
                //currency: '$'
                var content =  '$' + parseFloat(cashCount).toFixed(2)
                showAlert('Arqueo de Caja', content);
            });
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

        function getCurrentCashRegistrys() {
            CashRegisterResource.cashRegister(token).query({
                query: "CURRENT",
                branchId: vm.branchId,
                windowsId: vm.windowsId,
                offset: 0,
                limit: 100,
                tenantId: vm.tenantId
            }).$promise.then(function (result) {
                console.log(result);
                vm.registrys = result;

            });
        };

    }
})();
