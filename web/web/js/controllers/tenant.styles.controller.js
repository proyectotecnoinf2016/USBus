/**
 * Created by Lucia on 6/21/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('TenantController', TenantController);
    TenantController.$inject = ['$scope'];
    /* @ngInject */
    function TenantController($scope) {
    	$scope.submitForm = submitForm;

		$scope.radioData = [{
			"color" : "Red",
			"label" : "1"
		} , {
			"color" : "Blue",
			"label" : "2"
		} , {
			"color" : "Gray",
			"label" : "3"
		} , {
			"color" : "Light_Gray",
			"label" : "2"
		} , {
			"color" : "Green",
			"label" : "2"
		} , {
			"color" : "Orange",
			"label" : "2"
		} , {
			"color" : "Pink",
			"label" : "2"
		} , {
			"color" : "Violet",
			"label" : "2"
		} , {
			"color" : "Yellow",
			"label" : "2"
		}];

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

		

		$scope.logo = null;
    	$scope.header = null;
    	$scope.$watch('logo.length',function(newVal,oldVal){
    		console.log($scope.logo);
        });
        $scope.$watch('header.length',function(newVal,oldVal){
    		console.log($scope.header);
        });

    	function submitForm() {
			$scope.style.busColor = 'Red';
			$scope.style.showBus = showBus;
			$scope.style.theme = theme;

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



			//alert($scope.header[0].lfFile);

			var ext = "data:" + $scope.header[0].lfFile.type + ";base64,";
			var file = $scope.header[0].lfFile;

			var reader = new window.FileReader();
			reader.readAsDataURL(file);
			var base64data = '';
			reader.onloadend = function() {
				base64data = reader.result;
				//console.log(base64data );
			}

			alert(ext);
			alert(file);

			/*
			var reader  = new FileReader();
			var read = reader.readAsDataURL($scope.header[0].lfFile);
			alert(read);
			console.log($scope.header[0].lfFile);*/
	    }

	    

	}
})();
