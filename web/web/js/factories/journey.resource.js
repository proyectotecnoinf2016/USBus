/**
 * Created by Lucia on 6/5/2016.
 */
(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('JourneyResource', JourneyResource);
    JourneyResource.$inject = ['$resource'];
    /* @ngInject */
    function JourneyResource($resource) {
        return $resource('/usbus/api/register/bus');

        //TODO: EDITAR RESOURCE URL
    }
})();
