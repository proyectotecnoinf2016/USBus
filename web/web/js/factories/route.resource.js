/**
 * Created by Lucia on 6/5/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('RouteResource', RouteResource);
    RouteResource.$inject = ['$resource'];
    /* @ngInject */
    function RouteResource($resource) {
        return $resource('/rest/api/tenant/:tenantId', {id:'@id', tenantId:'@tenantId'}, {
            'update': {
                method: 'PUT'
            }
        });
        //TODO: EDITAR RESOURCE URL
    }
})();
