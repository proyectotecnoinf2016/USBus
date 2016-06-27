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
    	$scope.logo = null;
    	$scope.header = null;
    	$scope.$watch('logo.length',function(newVal,oldVal){
    		console.log($scope.logo);
        });
        $scope.$watch('header.length',function(newVal,oldVal){
    		console.log($scope.header);
        });

    	function submitForm() {
			alert($scope.header.file);
	    }

	    

	}
})();
