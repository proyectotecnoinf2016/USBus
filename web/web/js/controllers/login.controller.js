/**
 * Created by Lucia on 4/26/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('LoginController', LoginController);
    LoginController.$inject = ['$scope', '$mdDialog', 'LoginUserResource', 'localStorage', 'BranchResource', '$location', '$rootScope', 'theme'];
    /* @ngInject */
    function LoginController($scope, $mdDialog, LoginUserResource, localStorage, BranchResource, $location, $rootScope, theme) {
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
        $scope.login = login;
        $scope.selectBranch = selectBranch;

        $scope.tenantName = '';

        $scope.theme = theme;

        $scope.urlArray = $location.path().split('/');
        var i = 0;
        while (i < $scope.urlArray.length && $scope.urlArray[i] != 'tenant') {
            i++;
        }

        if (i < $scope.urlArray.length && $scope.urlArray[i + 1] != null) {
            $scope.tenantName = $scope.urlArray[i + 1];
        }


        function queryBranch(name) {
            return BranchResource.branches(token).query({
                status: true,
                offset: 0,
                limit: 100,
                tenantId: $scope.tenantId,
                query: 'NAME',
                branchName: name
            }).$promise;
        }


        function login(data) {
            if (data != null && typeof data.username !== 'undefined') {
                data.tenantName = $scope.tenantName;

                LoginUserResource.Login(data, function (r) {
                    console.log(r);
                    BranchResource.branches(r.token).query({
                        status: true,
                        offset: 0,
                        limit: 100,
                        busStatus: 'ACTIVE',
                        tenantId: r.tenantId,
                        query: 'ALL'
                    }).$promise.then(function(result){
                        console.log(result)
                        $scope.branches = result;
                        console.log($scope.branches.length)
                        console.log($scope.branches)

                        localStorage.setData('token', r.token);
                        localStorage.setData('tenantId', r.tenantId);
                        localStorage.setData('userName', data.username);
                        localStorage.setData('tenantName', $scope.tenantName);
                        localStorage.setData('userRoles', r.roles);

                        if ($scope.branches != null
                            && $scope.branches != 'undefined'
                            && $scope.branches.length > 0
                             && (r.roles.includes("CASHIER")
                             || r.roles.includes("ADMINISTRATOR"))) {
                            selectBranch();
                        }
                        else {
                            showAlert('Exito!', 'Ha ingresado al sistema de forma exitosa');
                        }
                        $rootScope.$emit('menuOption', '');
                        $rootScope.$emit('login', '');
                        $rootScope.authorization = true;
                    })


                }, function (r) {
                    console.log(r);
                    showAlert('Error!', 'Ocurrió un error al procesar su petición');
                });

            }
            else {
                showAlert('Error', 'Ocurrió un error al procesar su petición');
            }
        }


        function cancel() {
            $mdDialog.cancel();
        };

        function showAlert(title, content) {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog
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

        function selectBranch(ev) {
            $mdDialog.show({
                templateUrl: 'templates/branch.selection.html',
                controller: 'BranchSelectionController',
                parent: angular.element(document.body),
                clickOutsideToClose: false,
                locals: {theme: $scope.theme}
            }).then(
                function (answer) {

                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });


        }

    }
})();
