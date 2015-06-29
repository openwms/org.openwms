'use strict';
define([
    'app'
], function (app) {

    var srv = function ($http, $q, $cookies, $base64, CoreConfig) {

        var result = {};

        result.login = function (rootScope, scope) {
            var delay = $q.defer();
            $http.defaults.headers.post['Auth-Token'] = $scope.getToken();
            $http.defaults.headers.common['Content-Type'] = 'application/json';
            $http.post(scope.rootUrl + CoreConfig.url.security.login, {username: scope.user.username, password: scope.user.password})
                .success(function (data, status, headers, config) {
                    if (status == 200) {
                        rootScope.authToken = response.data.user.token;
                        //todo
                        //$window.sessionStorage.setItem('aToken', response.data.user.token);
                    } else {
                      //  $window.sessionStorage.removeItem('aToken');
                       // delete $rootScope.authToken;
                    }
                    delay.resolve({data: data, status: status});
                })
                .error(function (data, status, headers, config) {
                    //$window.sessionStorage.removeItem('aToken');
//                    delete $rootScope.authToken;
                    delay.reject(new Error(status, config));
                });
            return delay.promise;
        };

        result.setCredentials = function (rootScope, scope, data) {
            var authdata = $base64.encode(scope.user.username + ':' + scope.user.password);

            rootScope.globals.security = {
                currentUser: {
                    username: scope.user.username,
                    authdata: authdata
                }
            };

            $http.defaults.headers.common['Authorization'] = 'Basic ' + authdata; // jshint ignore:line
            $cookies.put('global-security', $rootScope.globals.security);
        };

        result.clearCredentials = function (rootScope) {
            rootScope.globals.security = {};
            $cookies.remove('global-security');
            $http.defaults.headers.common.Authorization = 'Basic ';
        };

        return result;
    };

    app.factory('AuthenticationService', ['$http', '$q', '$cookies', '$base64', 'CoreConfig', srv]);

});
