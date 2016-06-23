/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('CreateBranchController', CreateBranchController);
    CreateBranchController.$inject = ['$scope', 'localStorage', 'BranchResource', '$mdDialog'];
    /* @ngInject */
    function CreateBranchController($scope, localStorage, BranchResource, $mdDialog) {
        $scope.createBranch = createBranch;
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.addWindow = addWindow;
        $scope.deleteWindow = deleteWindow;

        $scope.branch = [];
        $scope.windows = [];

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        function createBranch(branch) {
            branch.windows = $scope.windows;
            branch.active = true;
            BranchResource.branches(token).save({
                tenantId: $scope.tenantId

            }, branch,function (resp) {
                console.log(resp);
                showAlert('Exito!', 'Se ha creado su sucursal de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al registrar el TENANT');
            } );
        }

        function addWindow() {
            $scope.windows.push({index: $scope.windows.length + 1,tickets : false, parcels : false});
            console.log($scope.windows);
        }

        function deleteWindow(index) {
            $scope.windows.splice(index, 1);
            var i = 0;
            for (i = 0; i < $scope.windows.length; i++) {
                $scope.windows[i].index = i + 1;
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

        function cancel() {
            $mdDialog.cancel();
        };


    }
})();
