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

		$scope.tenantName = '';
        $scope.logo = null;
        $scope.header = null;
		$scope.showBus = false;

		if (typeof localStorage.getData('tenantName') !== 'undefined' && localStorage.getData('tenantName') != null) {
			$scope.tenantName = localStorage.getData('tenantName');
		}
        $scope.primaryColor = "purple";
        $scope.secondaryColor = "cyan";

		TenantResource.tenant('').get({
			tenantId: 0,
			tenantName: $scope.tenantName
		}).$promise.then(function (result) {
			$scope.style = result;
			$scope.busColor = $scope.style.busColor;
			$scope.showBus = $scope.style.showBus;

            if ($scope.style != null && $scope.style.theme != null) {
                var theme = $scope.style.theme;
                if (theme.includes("purple")) {
                    $scope.primaryColor = "purple";
                } else if (theme.includes("indigo")) {
                    $scope.primaryColor = "indigo";
                } else if (theme.includes("light-blue")) {
                    $scope.primaryColor = "light-blue";
                } else if (theme.includes("teal")) {
                    $scope.primaryColor = "teal";
                } else if (theme.includes("amber")) {
                    $scope.primaryColor = "amber";
                } else if (theme.includes("deep-orange")) {
                    $scope.primaryColor = "deep-orange";
                } else if (theme.includes("brown")) {
                    $scope.primaryColor = "brown";

                } else if (theme.includes("grey")) {
                    $scope.primaryColor = "grey";
                } else if (theme.includes("red")) {
                    $scope.primaryColor = "red";
                }

                if (theme.includes("pink")) {
                    $scope.secondaryColor = "pink";
                } else if (theme.includes("cyan")) {
                    $scope.secondaryColor = "cyan";
                } else if (theme.includes("lime")) {
                    $scope.secondaryColor = "lime";
                } else if (theme.includes("yellow")) {
                    $scope.secondaryColor = "yellow";
                }
            }
			$scope.theme = $scope.primaryColor + $scope.secondaryColor;

		});

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





		if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
			$scope.tenantId = localStorage.getData('tenantId');
		}

		var token = null;//localStorage.getData('token');
		if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
			token = localStorage.getData('token');
		}


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

			if ($scope.logo != null && $scope.logo !== 'undefined' && $scope.logo != '') {
                alert($scope.logo);
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
            else {
                $scope.style.logoB64 = null;
                $scope.style.logoExtension = null;
            }

			var header = '';
			if ($scope.header != null &&  $scope.header !== 'undefined' && $scope.header != '') {
                alert($scope.header);
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
            else {
                $scope.style.headerB64 = null;
                $scope.style.headerExtension = null;
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
