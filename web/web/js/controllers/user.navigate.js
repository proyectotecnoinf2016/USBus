/**
 * Created by Lucia on 6/21/2016.
 */
(function () {
    'use strict';
    angular.module('usbus').controller('UserController', UserController);
    UserController.$inject = ['localStorage', '$scope', 'HumanResource', 'gender', 'role', '$mdDialog'];
    /* @ngInject */
    function UserController(localStorage, $scope, HumanResource, gender, role, $mdDialog) {
        $scope.createUser = createUser;

        $scope.users = [];
        $scope.message = 'No se encontraron Usuarios para mostrar';

        $scope.tenantId = 0;
        if (typeof localStorage.getData('tenantId') !== 'undefined' && localStorage.getData('tenantId') != null) {
            $scope.tenantId = localStorage.getData('tenantId');
        }

        var token = null;//localStorage.getData('token');
        if (localStorage.getData('token') != null && localStorage.getData('token') != '') {
            token = localStorage.getData('token');
        }

        HumanResource.resources(token).query({
            offset: 0,
            limit: 100,
            tenantId: $scope.tenantId,
            query: 'ALL',
            status: true,
            active: true
        }).$promise.then(function(result) {
            console.log(result);
            $scope.users = [];

            var i = 0;
            for (i = 0; i < result.length; i++) {
                var user = result[i];
                user.birthDate = moment(user.birthDate).format('DD/MM/YYYY');
                user.gender = gender.getGenderToShow(user.gender);

                var roles = user.roles;
                var auxRoles = [];
                var j = 0;

                for (j = 0; j < roles.length; j++) {
                    var roleToShow = roles[j];
                    roleToShow = role.getRoleToShow(roleToShow);
                    auxRoles.push(roleToShow);
                }

                user.roles = auxRoles;

                $scope.users.push(user);

            }


        });

        function createUser(ev) {
            $mdDialog.show({
                controller : 'CreateUsersController',
                templateUrl : 'templates/user.create.html',
                parent : angular.element(document.body),
                targetEvent : ev,
                clickOutsideToClose : false,
                locals : {theme : $scope.theme}
            }).then(
                function(answer) {
                    var result = HumanResource.resources(token).query({
                        offset: 0,
                        limit: 100,
                        tenantId: $scope.tenantId,
                        query: 'ALL',
                        status: true,
                        active: true
                    });
                    $scope.status = 'Aca deberia hacer la query de nuevo';
                }, function() {
                    $scope.status = 'You cancelled the dialog.';
            });
        }


    }
})();