/**
 * Created by Lucia on 6/5/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('BranchController', BranchController);
    BranchController.$inject = ['$scope', '$mdDialog', 'BranchResource', '$rootScope', 'localStorage'];
    /* @ngInject */
    function BranchController($scope, $mdDialog, BranchResource, $rootScope, localStorage) {
        $scope.showBranches = showBranches;
        $scope.createBranch = createBranch;
        $scope.deleteBranch = deleteBranch;
        $rootScope.$emit('options', 'admin');

        $scope.message = '';
        $scope.tenantId = 0;
        $scope.branches = [];


        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        BranchResource.branches(token).query({
            status:true,
            offset: 0,
            limit: 100,
            busStatus: 'ACTIVE',
            tenantId: $scope.tenantId,
            query: 'ALL'
        }).$promise.then(function(result) {
            console.log(result);
            $scope.branches = result;

        });


        if ($scope.branches.length === 0) {
            $scope.message = 'No se han encontrado elementos que cumplan con el criterio solicitado';
        }

        function showBranches(item, ev) {
            $mdDialog.show({
                controller : 'EditBranchController',
                templateUrl : 'templates/branch.edit.html',
                locals:{branchToEdit: item, theme : $scope.theme},
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : false
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
                clickOutsideToClose : false,
                locals : {theme : $scope.theme}
            }).then(
                function(answer) {
                    $scope.branches = BranchResource.branches(token).query({
                        status:true,
                        offset: 0,
                        limit: 100,
                        branchStatus: 'ACTIVE',
                        tenantId: $scope.tenantId,
                        query: 'ALL'
                    });

                    $scope.status = 'Aca deberia hacer la query de nuevo';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function deleteBranch(item) {
            delete item["_id"];
            BranchResource.branches(token).delete({
                tenantId: $scope.tenantId,
                branchId: item.id}, item).$promise.then(function(data){
                console.log(data);
            }, function(error){
            });
            var index = 0;

            while (index < $scope.branches.length && item.name != $scope.branches[index].name) {
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