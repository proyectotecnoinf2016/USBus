/**
 * Created by jpmartinez on 09/05/16.
 */
/*(function() {
    'use strict';
    angular
        .module('usbus')
        .config(config);
    config.$inject = ['$mdThemingProvider'];
    /* @ngInject */
/*    function config($mdThemingProvider) {

        $mdThemingProvider.theme('dark', 'default')
            .primaryPalette('yellow')
            .dark();
    }
})();*/
(function() {
    'use strict';
    angular
        .module('usbus')
        .config(config);
    config.$inject = ['$mdThemingProvider'];
    /* @ngInject */
    function config($mdThemingProvider) {
        $mdThemingProvider.theme('purplepink').primaryPalette('purple').accentPalette('pink');
        $mdThemingProvider.theme('purplecyan').primaryPalette('purple').accentPalette('cyan');
        $mdThemingProvider.theme('purplelime').primaryPalette('purple').accentPalette('lime');
        $mdThemingProvider.theme('purpleyellow').primaryPalette('purple').accentPalette('yellow');
        $mdThemingProvider.theme('purplegrey').primaryPalette('purple').accentPalette('grey');
        $mdThemingProvider.theme('purplepurple').primaryPalette('purple').accentPalette('purple');


        $mdThemingProvider.theme('indigopink').primaryPalette('indigo').accentPalette('pink');
        $mdThemingProvider.theme('indigocyan').primaryPalette('indigo').accentPalette('cyan');
        $mdThemingProvider.theme('indigolime').primaryPalette('indigo').accentPalette('lime');
        $mdThemingProvider.theme('indigoyellow').primaryPalette('indigo').accentPalette('yellow');
        $mdThemingProvider.theme('indigogrey').primaryPalette('indigo').accentPalette('grey');
        $mdThemingProvider.theme('indigopurple').primaryPalette('indigo').accentPalette('purple');


        $mdThemingProvider.theme('light-bluepink').primaryPalette('light-blue').accentPalette('pink');
        $mdThemingProvider.theme('light-bluecyan').primaryPalette('light-blue').accentPalette('cyan');
        $mdThemingProvider.theme('light-bluelime').primaryPalette('light-blue').accentPalette('lime');
        $mdThemingProvider.theme('light-blueyellow').primaryPalette('light-blue').accentPalette('yellow');
        $mdThemingProvider.theme('light-bluegrey').primaryPalette('light-blue').accentPalette('grey');
        $mdThemingProvider.theme('light-bluepurple').primaryPalette('light-blue').accentPalette('purple');


        $mdThemingProvider.theme('tealpink').primaryPalette('teal').accentPalette('pink');
        $mdThemingProvider.theme('tealcyan').primaryPalette('teal').accentPalette('cyan');
        $mdThemingProvider.theme('teallime').primaryPalette('teal').accentPalette('lime');
        $mdThemingProvider.theme('tealyellow').primaryPalette('teal').accentPalette('yellow');
        $mdThemingProvider.theme('tealgrey').primaryPalette('teal').accentPalette('grey');
        $mdThemingProvider.theme('tealpurple').primaryPalette('teal').accentPalette('purple');


        $mdThemingProvider.theme('amberpink').primaryPalette('amber').accentPalette('pink');
        $mdThemingProvider.theme('ambercyan').primaryPalette('amber').accentPalette('cyan');
        $mdThemingProvider.theme('amberlime').primaryPalette('amber').accentPalette('lime');
        $mdThemingProvider.theme('amberyellow').primaryPalette('amber').accentPalette('yellow');
        $mdThemingProvider.theme('ambergrey').primaryPalette('amber').accentPalette('grey');
        $mdThemingProvider.theme('amberpurple').primaryPalette('amber').accentPalette('purple');


        $mdThemingProvider.theme('deep-orangepink').primaryPalette('deep-orange').accentPalette('pink');
        $mdThemingProvider.theme('deep-orangecyan').primaryPalette('deep-orange').accentPalette('cyan');
        $mdThemingProvider.theme('deep-orangelime').primaryPalette('deep-orange').accentPalette('lime');
        $mdThemingProvider.theme('deep-orangeyellow').primaryPalette('deep-orange').accentPalette('yellow');
        $mdThemingProvider.theme('deep-orangegrey').primaryPalette('deep-orange').accentPalette('grey');
        $mdThemingProvider.theme('deep-orangepurple').primaryPalette('deep-orange').accentPalette('purple');


        $mdThemingProvider.theme('brownpink').primaryPalette('brown').accentPalette('pink');
        $mdThemingProvider.theme('browncyan').primaryPalette('brown').accentPalette('cyan');
        $mdThemingProvider.theme('brownlime').primaryPalette('brown').accentPalette('lime');
        $mdThemingProvider.theme('brownyellow').primaryPalette('brown').accentPalette('yellow');
        $mdThemingProvider.theme('browngrey').primaryPalette('brown').accentPalette('grey');
        $mdThemingProvider.theme('brownpurple').primaryPalette('brown').accentPalette('purple');


        $mdThemingProvider.theme('greypink').primaryPalette('grey').accentPalette('pink');
        $mdThemingProvider.theme('greycyan').primaryPalette('grey').accentPalette('cyan');
        $mdThemingProvider.theme('greylime').primaryPalette('grey').accentPalette('lime');
        $mdThemingProvider.theme('greyyellow').primaryPalette('grey').accentPalette('yellow');
        $mdThemingProvider.theme('greygrey').primaryPalette('grey').accentPalette('grey');
        $mdThemingProvider.theme('greypurple').primaryPalette('grey').accentPalette('purple');

        $mdThemingProvider.theme('redpink').primaryPalette('red').accentPalette('pink');
        $mdThemingProvider.theme('redcyan').primaryPalette('red').accentPalette('cyan');
        $mdThemingProvider.theme('redlime').primaryPalette('red').accentPalette('lime');
        $mdThemingProvider.theme('redyellow').primaryPalette('red').accentPalette('yellow');
        $mdThemingProvider.theme('redgrey').primaryPalette('red').accentPalette('grey');
        $mdThemingProvider.theme('redpurple').primaryPalette('red').accentPalette('purple');

        $mdThemingProvider.alwaysWatchTheme(true);
    }
})();
