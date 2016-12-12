/**
 * Created by jpmartinez on 12/05/16.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('IndexController', IndexController);
    IndexController.$inject = ['$scope', '$mdDialog', 'localStorage', '$location', '$rootScope', 'TenantResource', '$geolocation'];
    /* @ngInject */
    function IndexController($scope, $mdDialog, localStorage, $location, $rootScope, TenantResource, $geolocation) {
        moment.locale('es');
        console.log(moment.locale());
        $scope.theme = 'indigopink';
        $scope.style = '';
        $scope.show = false;
        $scope.logo = 'img/USBus2.png';
        //$scope.showBus = true;
        $scope.busColor = 'Red';


        $scope.tenantName = 'USBus';
        $scope.userName = 'Invitado';

        if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
            $scope.tenantName = localStorage.getData('tenantName');
        }
        if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
            $scope.userName = localStorage.getData('userName');
        }

        $scope.logout = logout;
        $scope.showAlert = showAlert;
        $rootScope.$on('login', function (event, data) {
            if (localStorage.getData('tenantName') != null && localStorage.getData('tenantName') != '') {
                $scope.tenantName = localStorage.getData('tenantName');
            }
            if (localStorage.getData('userName') != null && localStorage.getData('userName') != '') {
                $scope.userName = localStorage.getData('userName');
            }
        });


        $scope.login = login;
        $scope.redirectTo = redirectTo;

        $scope.urlArray = $location.path().split('/');
        var i = 0;
        while (i < $scope.urlArray.length && $scope.urlArray[i] != 'tenant') {
            i++;
        }

        if (i < $scope.urlArray.length && $scope.urlArray[i + 1] != null) {
            $scope.tenantName = $scope.urlArray[i + 1];
        }

        if ($scope.style == '' && $scope.tenantName != 'USBus') {
            TenantResource.tenant('').get({
                tenantId: 0,
                tenantName: $scope.tenantName
            }).$promise.then(function (result) {
                $scope.style = result;
                localStorage.setData('humanResourcesURL', $scope.style.humanResourcesURL);
                localStorage.setData('accountingURL', $scope.style.accountingURL);
                $scope.theme = $scope.style.theme;
                //$scope.showBus = $scope.style.showBus;
                //console.log($scope.showBus);
                if ($scope.style.logoB64 != null && $scope.style.logoB64 != 'undefined' && $scope.style.logoB64 != '') {
                    $scope.logo = 'data:image/' + $scope.style.logoExtension + ';base64,' + $scope.style.logoB64;
                }

                $scope.busColor = $scope.style.busColor;
            });
        }


        $rootScope.$on('theme', function (event, data) {
            $scope.theme = data;
            //$rootScope.$broadcast('customTheme', data);
        });


        $scope.menuOptions = [];

        $rootScope.$on('options', function (event, data) {
            var options = '';
            if (data == 'admin') {

                options = [{
                    name: "Planificar Viajes",
                    url: "admin",
                    icon: "event_seat"
                }, {
                    name: "Administrar Usuarios",
                    url: "admin/users",
                    icon: "perm_identity"
                }, {
                    name: "Administrar Sucursales",
                    url: "admin/branch",
                    icon: "location_city"
                }, {
                    name: "Administrar Unidades",
                    url: "admin/bus",
                    icon: "directions_bus"
                }, {
                    name: "Administrar Paradas",
                    url: "admin/busStop",
                    icon: "store_mall_directory"
                }, {
                    name: "Administrar Rutas",
                    url: "admin/route",
                    icon: "terrain"
                }, {
                    name: "Administrar Servicios",
                    url: "admin/service",
                    icon: "subway"
                }, {
                    name: "Personalizarción",//<i class="material-icons">format_color_fill</i>
                    url: "admin/styles",
                    icon: "format_color_fill"
                }];
            }

            if (data == 'tickets') {
                options = [{
                    name: "Venta/Reserva de Pasajes",
                    url: "tickets",
                    icon: "airline_seat_recline_normal" //<i class="material-icons">add_shopping_cart</i><i class="material-icons">airline_seat_recline_normal</i>
                }, {
                    name: "Cancelar Venta/Reserva",
                    url: "tickets/cancel",
                    icon: "signal_cellular_no_sim"
                }, //{
                    // name: "Encomiendas",
                    // url: "parcels",
                    // icon: "unarchive"
                // },
                    {
                    name: "Caja",
                    url: "tickets/window",
                    icon: "account_balance_wallet"
                }

                ];

            }

            $scope.menuOptions = [];


            if ($scope.tenantName !== 'USBus') {
                var i = 0;
                while (i < options.length) {
                    $scope.menuOptions.push(options[i]);
                    i++;
                }

            }
            else {
                $scope.menuOptions = [];
            }

        });


        function login(ev) {
            $mdDialog.show({
                controller: 'LoginController',
                templateUrl: 'templates/login.html',
                locals: {theme: $scope.theme},
                parent: angular.element(document.body),
                targetEvent: ev,
                clickOutsideToClose: false
            }).then(
                function (answer) {
                    $scope.status = 'You said the information was "'
                        + answer + '".';
                    $rootScope.$broadcast('login', 'success');
                }, function () {
                    $scope.status = 'You cancelled the dialog.';
                });
        };

        function redirectTo(redirectUrl) {
            var urlArray = $location.path().split('/');
            var url = '';
            var i = 0;

            while (i < urlArray.length && urlArray[i] != $scope.tenantName) {
                url = url + urlArray[i] + '/';
                i++;
            }

            if (i < urlArray.length) {
                url = url + urlArray[i] + '/';
            }
            url = url + redirectUrl;
            $location.path(url);
        }


        $geolocation.getCurrentPosition({
            timeout: 60000
        }).then(function (position) {
            $scope.myPosition = position;
            console.log($scope.myPosition);
            localStorage.setData('location', $scope.myPosition);
        });


        function logout() {
            localStorage.clear();
            //$scope.tenantName = 'USBus';
            $scope.userName = 'Invitado';

            $rootScope.$emit('logout', '');

            showAlert('', '¡Hasta Luego!')
            redirectTo('');
        }


        // function showAlert(title, body, ev) {
        //     var useFullScreen = false;
        //     $mdDialog.show({
        //         template: '</p><img src="https://encrypted-tbn3.gstatic.com/images?q=tbn:ANd9GcTGZqvke96TnMbyRvGxwCiccHvsv6cY4vLGoaSTJggL79zTCO8axg"/>',
        //         parent: angular.element(document.body),
        //         targetEvent: ev,
        //         clickOutsideToClose: true,
        //         fullscreen: useFullScreen
        //     })
        //
        // };
        function showAlert(title,content) {
            // Appending dialog to document.body to cover sidenav in docs app
            // Modal dialogs should fully cover application
            // to prevent interaction outside of dialog
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
