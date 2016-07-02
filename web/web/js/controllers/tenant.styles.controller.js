/**
 * Created by Lucia on 6/21/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('TenantController', TenantController);
    TenantController.$inject = ['$scope', '$rootScope', 'TenantResource', 'localStorage', '$mdDialog'];
    /* @ngInject */
    function TenantController($scope, $rootScope, TenantResource, localStorage, $mdDialog) {
    	$scope.submitForm = submitForm;
        $scope.themeChange = themeChange;
		$scope.showAlert = showAlert;

		$scope.radioData = [
			"Red",
			"Blue",
			"Gray",
			"Light_Gray",
			"Green",
			"Orange",
			"Pink",
			"Violet",
			"Yellow"];


		$scope.primaryData = [{
			"value" : "purple",
			"label" : "Violeta"
		} , {
			"value" : "indigo",
			"label" : "Índigo"
		} , {
			"value" : "light-blue",
			"label" : "Celeste"
		} , {
			"value" : "teal",
			"label" : "Verde azulado"
		} , {
			"value" : "amber",
			"label" : "Ámbar"
		} , {
			"value" : "deep-orange",
			"label" : "Naranja"
		} , {
			"value" : "brown",
			"label" : "Marrón"
		} , {
			"value" : "grey",
			"label" : "Gris"
		} , {
			"value" : "red",
			"label" : "Rojo"
		}];

		$scope.secondaryData = [{
			"value" : "pink",
			"label" : "Rosado"
		} , {
			"value" : "cyan",
			"label" : "Cyan"
		} , {
			"value" : "lime",
			"label" : "Lima"
		} , {
			"value" : "yellow",
			"label" : "Amarillo"
		}];



		$scope.busColor = "Red";
		$scope.primaryColor = "purple";
		$scope.secondaryColor = "cyan";

		$scope.theme = $scope.primaryColor + $scope.secondaryColor;

		if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
			$scope.tenantId = localStorage.getData('tenantId');
		}

		var token = null;//localStorage.getData('token');
		if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
			token = localStorage.getData('token');
		}

		$scope.logo = null;
    	$scope.header = null;
    	$scope.$watch('logo.length',function(newVal,oldVal){
    		console.log($scope.logo);
        });
        $scope.$watch('header.length',function(newVal,oldVal){
    		console.log($scope.header);
        });

        function themeChange() {
            $scope.style.theme = $scope.primaryColor + $scope.secondaryColor;
            $rootScope.$emit('theme', $scope.style.theme);
        }
        

    	function submitForm() {
			var i = 0;
			var logo = '';
            $scope.style.busColor = $scope.busColor;
            $scope.theme = $scope.primaryColor + $scope.secondaryColor;

			if ($scope.logo != null &&  $scope.logo !== 'undefined') {
                for (i = 0; i < $scope.logo.length; i++) {
                    logo = $scope.logo[i].lfFile;
					var reader = new window.FileReader();
					reader.readAsDataURL(logo);
					reader.onloadend = function() {
						$scope.style.logoB64 = reader.result;
						$scope.style.logoExtension = logo.type;
						//console.log(base64data );

						$scope.style.logoExtension = logo.type.split("/")[1];
						$scope.style.logoB64 = reader.result.split(",")[1];
					}

				}
			}


			var header = '';
			if ($scope.header != null &&  $scope.header !== 'undefined') {
				for (i = 0; i < $scope.header.length; i++) {
                    header = $scope.header[i].lfFile;
					var reader2 = new window.FileReader();
					reader2.readAsDataURL(header);
					reader2.onloadend = function() {
						$scope.style.headerB64 = reader2.result;
						$scope.style.headerExtension = header.type;
						//console.log($scope.style.headerB64 );
						$scope.style.headerExtension = header.type.split("/")[1];
						$scope.style.headerB64 = reader2.result.split(",")[1];
					}
				}


				console.log($scope.style);
			}


			TenantResource.tenant(token).update({
				tenantId: $scope.tenantId
			}, $scope.style,function (resp) {
				console.log(resp);
				showAlert('Exito!', 'Se ha editado el estilo de forma exitosa');
			}, function (error) {
				console.log(error);
				showAlert('Error!', 'Ocurrió un error al procesar su solicitud');
			} );

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
	    

	}
})();
