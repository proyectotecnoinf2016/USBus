/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateCashEntryController', CreateCashEntryController);
    CreateCashEntryController.$inject = ['$scope', 'localStorage', 'CashRegisterResource', '$mdDialog', 'theme'];
    /* @ngInject */
    function CreateCashEntryController($scope, localStorage, CashRegisterResource, $mdDialog, theme) {
        var vm = this;
        var token = null;
        init();

        vm.createEntry = createEntry;
        vm.cancel = cancel;
        vm.showAlert = showAlert;
        vm.cashRegister = {};


        /*****************************************************************************************************/
        /*******************************************   FUNCIONES   *******************************************/
        /*****************************************************************************************************/
        function init() {
            vm.theme = theme;
            vm.tenantId = localStorage.getData('tenantId');
            token = localStorage.getData('token');
            vm.branchId = localStorage.getData('branchId');
            vm.windowsId = localStorage.getData('windowsId');
            console.log(vm.tenantId)
        }

        function createEntry(cashRegister) {
            cashRegister.tenantId = vm.tenantId;
            cashRegister.branchId = vm.branchId;
            cashRegister.windowsId = vm.windowsId;
            cashRegister.sellerName = vm.userName;
            cashRegister.date = new Date();
            cashRegister.origin = "CASH_REGISTER";
            cashRegister.payment = "CASH";
            cashRegister.type = "ENTRY";
            CashRegisterResource.cashRegister(token).save({tenantId: vm.tenantId}, cashRegister, function () {
                showAlert('Exito!', 'Se creó la entrada de caja.');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al crear la entrada de caja');
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
                    .ariaLabel('Alert Dialog').ok('Cerrar'));
        };
        function cancel() {
            $mdDialog.cancel();
        };

    }
})();
