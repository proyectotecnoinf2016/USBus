/**
 * Created by Lucia on 6/7/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('ServiceResource', ServiceResource);
    ServiceResource.$inject = ['$resource'];
    /* @ngInject */
    function ServiceResource($resource) {
        return $resource('/usbus/api/tenant/:tenantId', {id:'@id', tenantId:'@tenantId'}, {
            'update': {
                method: 'PUT'
            }
        });
        //TODO: EDITAR RESOURCE URL
    }
})();
