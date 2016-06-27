/**
 * Created by Lucia on 6/21/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('TenantController', TenantController);
    TenantController.$inject = ['$scope'];
    /* @ngInject */
    function TenantController($scope) {
    	$scope.upload = upload;

    	function upload(file) {
			console.log (file);
		    if (file) {
		        // create an object for the ids
			      var pictureId;

			      // create a new formdata to store our image
			      var fd = new FormData();
			      fd.append('photo', file);
			      
			      console.log (fd);
		    }
	    }
	}
})();
