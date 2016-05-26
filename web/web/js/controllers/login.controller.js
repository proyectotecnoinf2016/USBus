/**
 * Created by Lucia on 4/26/2016.
 */
(function() {
    'use strict';
    angular.module('usbus').controller('LoginController', LoginController);
    LoginController.$inject = [ '$scope', '$mdDialog'];
    /* @ngInject */
    function LoginController($scope, $mdDialog) {
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;

        function cancel() {
            $mdDialog.cancel();
        };

        function showAlert(title,content) {
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

    }})();

