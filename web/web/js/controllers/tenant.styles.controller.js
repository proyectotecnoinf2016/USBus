/**
 * Created by Lucia on 6/21/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('TenantController', TenantController);
    TenantController.$inject = ['$scope', 'TenantResource', 'localStorage'];
    /* @ngInject */
    function TenantController($scope, TenantResource, localStorage) {
    	$scope.submitForm = submitForm;

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
			"label" : "Ámber"
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


		$scope.style = [];

		$scope.style.busColor = "Red"
		$scope.primaryColor = "purple";
		$scope.secondaryColor = "cyan";

		$scope.style.theme = $scope.primaryColor + $scope.secondaryColor;

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

    	function submitForm() {
			var i = 0;
			var file = '';
			if ($scope.logo != null &&  $scope.logo !== 'undefined') {
				for (i = 0; i < $scope.logo.length; i++) {
					file = $scope.logo[i].lfFile;
					var reader = new window.FileReader();
					reader.readAsDataURL(file);
					reader.onloadend = function() {
						$scope.style.logoB64 = reader.result;
						$scope.style.logoExtension = file.type;
						//console.log(base64data );
					}

				}
			}


			file = '';
			if ($scope.header != null &&  $scope.header !== 'undefined') {
				for (i = 0; i < $scope.header.length; i++) {
					file = $scope.header[i].lfFile;
					var reader = new window.FileReader();
					reader.readAsDataURL(file);
					reader.onloadend = function() {
						$scope.style.headerB64 = reader.result;
						$scope.style.headerExtension = file.type;
						//console.log($scope.style.headerB64 );
					}
				}
			}


			TenantResource.tenant(token).update({
				tenantId: $scope.tenantId
			}, $scope.style,function (resp) {
				console.log(resp);
				showAlert('Exito!', 'Se ha editado su unidad de forma exitosa');
			}, function (error) {
				console.log(error);
				showAlert('Error!', 'Ocurrió un error al editar la Unidad');
			} );

	    }

	    

	}
})();
