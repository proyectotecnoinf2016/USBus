/**
 * Created by Lucia on 4/26/2016.
 */
(function() {
    'use strict';
    angular.module('usbus').controller('LoginController', LoginController);
    LoginController.$inject = [ '$scope', '$mdDialog', 'LoginUserResource'];
    /* @ngInject */
    function LoginController($scope, $mdDialog, LoginUserResource) {
        $scope.cancel = cancel;
        $scope.showAlert = showAlert;
		$scope.login = login;
		
		function login(data) {
			data.tenantId = "999";
			
			// {"tenantId":"999","username":"usu2","password":""}
			
			if (data != null && typeof data.username !== 'undefined') {
	    		LoginUserResource.query(data,function(){
	    		showAlert('Exito!','Ha ingresado al sistema de forma exitosa');
				}, function(r){
					console.log(r);
					showAlert('Error!','Ocurrió un error al procesar su petición');
				});
	    		
    		}
    		else {
    			showAlert('Error', 'Ocurrió un error al procesar su petición');
    		}
		}

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

