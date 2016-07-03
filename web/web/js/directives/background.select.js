/**
 * Created by Lucia on 6/19/2016.
 */

(function() {
    'use strict';
    angular
        .module('usbus')
        .directive('backImg', backImg);
    backImg.$inject = [];
    /* @ngInject */
    function backImg() {

        return function($scope, element, attrs){
            var url = attrs.backImg;
            console.log(url);
            element.css({
                'background-image': 'url(' + url +')',
                'background-size' : '100% 100%'
            });
        };
    }
})();