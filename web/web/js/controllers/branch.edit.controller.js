/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditBranchController', EditBranchController);
    EditBranchController.$inject = ['$scope', 'BranchResource', '$mdDialog', 'branchToEdit', 'theme'];
    /* @ngInject */
    function EditBranchController($scope, BranchResource, $mdDialog, branchToEdit, theme) {
        $scope.branch = branchToEdit;
        $scope.tenantId = 0;
        $scope.theme = theme;

        $scope.windows = $scope.branch.windows;
        

        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.addWindow = addWindow;
        $scope.deleteWindow = deleteWindow;

        function updateBranch(item) {
            BranchResource.update({id: item.id, tenantId: $scope.tenantId}, item).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
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
