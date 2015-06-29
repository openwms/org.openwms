'use strict';
define([
    'app',
    'services/ResponsedataResolver'
], function (app) {

    var srv = function ($http, $q, $base64, CoreConfig, ResponsedataResolver) {

        var coreServiceFactory = {};

        coreServiceFactory.add = function (url, $scope, entity, h) {
            var delay = $q.defer();
            var cfg = (h === undefined) ? {method: 'POST'} : {method: 'POST', headers: h};
            $http.defaults.headers.put['Auth-Token'] = $scope.getToken();
            $http.post($scope.rootUrl + url, entity, cfg)
                .success(function (data, status) {
                    if (status < 200 || status >= 300) {
                        throwError(data, status, delay);
                    }
                    delay.resolve(data.items[0].obj[0]);
                })
                .error(function (data, status) {
                    throwError(data, status, delay);
                });
            return delay.promise;
        };

        coreServiceFactory.delete = function (url, $scope, h) {
            var delay = $q.defer();
            $http({method: 'DELETE', url: $scope.rootUrl + url, headers: h})
                .success(function () {
                    delay.resolve();
                })
                .error(function (data, status, headers, config) {
                    delay.reject(new Error(status, config));
                });
            return delay.promise;
        };

        coreServiceFactory.save = function (url, $scope, entity) {
            var delay = $q.defer();
            $http.defaults.headers.put['Auth-Token'] = $scope.getToken();
            $http.defaults.headers.put['Tenant'] = $scope.getTenant();
            $http.defaults.headers.common['Content-Type'] = 'application/json';
            $http.put($scope.rootUrl + url, entity)
                .success(function (data) {
                    if (data.items === undefined || data.items[0].httpStatus !== 200) {
                        console.log("Error in service all for url [" + url + "] with entity [" + entity + "]. Response is:" + data);
                        delay.reject(new Error(data.items[0].message, data.items[0].httpStatus));
                    } else {
                        delay.resolve(data.items[0].obj[0]);
                    }
                })
                .error(function (data, status, headers, config) {
                    delay.reject(new Error(status, config));
                });
            return delay.promise;
        };

        coreServiceFactory.getAll = function (url, $scope, h) {
            var delay = $q.defer();
            //$http.defaults.headers.get['Auth-Token'] = $scope.authToken;
            $http({method: 'GET', url: $scope.rootUrl + url, headers: h})
                .success(function (data) {
                    ResponsedataResolver.resolveMultiple(data, delay);
                })
                .error(function (data, status, headers, config) {
                    delay.reject(new Error(status, config));
                });
            return delay.promise;
        };

        var throwError = function throwError(data, status, q) {
            var err = new Error(status, data);
            err.data = {
                httpStatus: data.items[0].httpStatus,
                message: data.items[0].message
            };
            q.reject(err);
        };

        return coreServiceFactory;

    };

    app.factory('CoreService', ['$http', '$q', '$base64', 'CoreConfig', 'ResponsedataResolver', srv]);

});
