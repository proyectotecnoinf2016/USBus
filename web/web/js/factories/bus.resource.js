/**
 * Created by Lucia on 6/5/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('BusResource', BusResource);
    BusResource.$inject = ['$resource'];
    /* @ngInject */
    function BusResource($resource) {
        return $resource('/usbus/api/tenant/:tenantId', {id:'@id', tenantId:'@tenantId'}, {
            'update': {
                method: 'PUT'
            }
        });
        //TODO: EDITAR RESOURCE URL
    }
})();
