'use strict';

define([
    'app',
    'services/CoreService',
    'services/AuthenticationService'
], function (app) {

    var loginController = function ($scope, $rootScope, $http, $location, $window, CoreService, CoreConfig, AuthenticationService) {
        // This object will be filled by the form
        $scope.user = {};
        AuthenticationService.clearCredentials($rootScope);

        // Register the login() function
        $scope.login = function () {

            $scope.modal.opened = false;

            $scope.dataLoading = true;
            AuthenticationService.login($rootScope, $scope)
                .then(function (response) {
                    if (response.httpStatus === '200') {
                        AuthenticationService.setCredentials($rootScope, $scope, response.data);
                        $('#loginDialog').modal('hide');
                        $rootScope.user = $scope.user.username;
                        $rootScope.message = undefined;
                        $location.url('/dossier');
//					$location.path('/');
                    } else {
                        $rootScope.message = 'Authentication failed.';
                        $scope.modal.opened = true;
                    }
                },function (e) {
                    $scope.error = response.message;
                    $scope.dataLoading = false;
                    $rootScope.message = 'Authentication failed.';
                    $scope.modal.opened = true;
                });
        };
    };

    app.register.controller('LoginController', ['$scope', '$rootScope', '$http', '$location', '$window', 'CoreService', 'CoreConfig', 'AuthenticationService', loginController]);
});
