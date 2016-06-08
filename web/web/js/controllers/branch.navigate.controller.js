/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('BranchController', BranchController);
    BranchController.$inject = ['$scope', '$mdDialog', 'BranchResource'];
    /* @ngInject */
    function BranchController($scope, $mdDialog, BranchResource) {
        $scope.showBranches = showBranches;
        $scope.createBranch = createBranch;
        $scope.deleteBranch = deleteBranch;

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.branches = [{
            'name': '1'
        }, {
            'name': '2'
        }];

        if ($scope.branches.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }

        function showBranches(item, ev) {
            $mdDialog.show({
                controller : 'EditBranchController',
                templateUrl : 'templates/branch.edit.html',
                locals:{branchToEdit: item},
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : true
            }).then(
                function(answer) {
                    $scope.status = 'You said the information was "'
                        + answer + '".';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function createBranch(ev) {
            $mdDialog.show({
                controller : 'CreateBranchController',
                templateUrl : 'templates/branch.create.html',
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : true
            }).then(
                function(answer) {
                    $scope.status = 'Aca deberia hacer la query de nuevo';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function deleteBranch(text) {
            //TODO: ver si aca va el id, el name o quien (supongo que va el id nomas);
            /*
            bus.active = false;
            BranchResource.update({id: bus.id, tenantId: $scope.tenantId}, bus).$promise.then(function(data){
                showAlert('Exito!','Se ha editado su almac&eacute;n virtual de forma exitosa');
                console.log(style);
            }, function(error){
                showAlert('Error!','Ocurri&oacute; un error al procesar su petici&oacute;n');
            });
            */
            var index = 0;

            while (index < $scope.branches.length && text != $scope.branches[index].name) {
                index++;
            }

            if (index < $scope.branches.length) {
                $scope.branches.splice(index, 1);
            }

            if ($scope.branches.length === 0) {
                $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado.';
            }

        }

    }
})();