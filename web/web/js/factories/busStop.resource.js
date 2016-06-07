/**
 * Created by Lucia on 6/5/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .factory('BusStopResource', BusStopResource);
    BusStopResource.$inject = ['$resource'];
    /* @ngInject */
    function BusStopResource($resource) {
        return $resource('/usbus/api/tenant/:tenantId', {id:'@id', tenantId:'@tenantId'}, {
            'update': {
                method: 'PUT'
            }
        });
        //TODO: EDITAR RESOURCE URL
    }
})();
