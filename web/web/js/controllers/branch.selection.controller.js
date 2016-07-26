/**
 * Created by Lucia on 7/10/2016.
 */
(function() {
    'use strict';
    angular.module('usbus').controller('BranchSelectionController', BranchSelectionController);
    BranchSelectionController.$inject = [ '$scope', '$mdDialog', 'BranchResource','localStorage', '$location', '$rootScope', 'theme'];
    /* @ngInject */
    function BranchSelectionController($scope, $mdDialog, BranchResource, localStorage, $location, $rootScope, theme) {
        $scope.cancel = cancel;
        $scope.selectBranch = selectBranch;
        $scope.queryBranch = queryBranch;
        $scope.showAlert = showAlert;


        localStorage.setData('location', $scope.myPosition);

        $scope.branch = '';

        $scope.theme = theme;

        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        function queryBranch(name) {
            return BranchResource.branches(token).query({
                status:true,
                offset: 0,
                limit: 100,
                tenantId: $scope.tenantId,
                query: 'NAME',
                branchName: name
            }).$promise;
        }



        function cancel() {
            $mdDialog.cancel();
        };



        function selectBranch() {
            localStorage.setData('branchId', $scope.branch.id);
            showAlert('Exito!', 'Ha ingresado al sistema de forma exitosa')
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



    }})();
