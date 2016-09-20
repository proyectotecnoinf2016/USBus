/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditBranchController', EditBranchController);
    EditBranchController.$inject = ['$scope', 'BranchResource', '$mdDialog', 'branchToEdit', 'theme', 'localStorage'];
    /* @ngInject */
    function EditBranchController($scope, BranchResource, $mdDialog, branchToEdit, theme, localStorage) {
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.addWindow = addWindow;
        $scope.deleteWindow = deleteWindow;
        $scope.updateBranch = updateBranch;

        $scope.branch = branchToEdit;
        $scope.tenantId = 0;
        $scope.theme = theme;

        $scope.windows = $scope.branch.windows;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }



        function updateBranch(item) {
            item.windows = $scope.windows;
            BranchResource.branches(token).update({
                tenantId: $scope.tenantId,
                branchId: item.id
            }, item,function (resp) {
                console.log(resp);
                showAlert('Exito!', 'Se ha editado la sucursal de forma exitosa');
            }, function (error) {
                console.log(error);
                showAlert('Error!', 'Ocurrió un error al editar la Parada');
            } );


        }


        function addWindow() {
            if ($scope.windows == null) {
                $scope.windows = [];
            }

            $scope.windows.push({tickets : false, parcels : false, active: true});
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
