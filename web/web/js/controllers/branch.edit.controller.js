/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('EditBranchController', EditBranchController);
    EditBranchController.$inject = ['$scope', 'BranchResource', '$mdDialog', 'branchToEdit'];
    /* @ngInject */
    function EditBranchController($scope, BranchResource, $mdDialog, branchToEdit) {
        $scope.branch = branchToEdit;
        $scope.tenantId = 0;
        

        $scope.cancel = cancel;
        $scope.showAlert = showAlert;

        function updateBranch(item) {
            BranchResource.update({id: item.id, tenantId: $scope.tenantId}, item).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
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
