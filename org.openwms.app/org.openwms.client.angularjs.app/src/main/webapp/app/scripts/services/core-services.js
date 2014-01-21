'use strict';

var servicesModule = angular.module('openwms_services', ['ngResource']);

servicesModule.factory('rolesService',
	function($resource, $http) {
		var result = {
			getAllRoles : function($http, $scope) {
				$http.defaults.headers.common['Auth-Token'] = $scope.authToken;
				$http.get($scope.rootUrl+'/role').success(function (data, status, headers, config) {
					$scope.roleEntities = data;
				});
			}
		}
		return result;
	});

/*
servicesModule.factory('UsersService', ['$resource',
	function($resource) {
		return $resource('/user/:query', {query: '@query'});
}]);

servicesModule.factory('GetUsers', ['UsersService', '$q',
	function(UsersService, $q) {
		return function() {
			var delay = $q.defer();
			UsersService.query(function(users) {
				delay.resolve(users);
			}, function() {
				delay.reject('Unable to fetch recipes');
			});
			return delay.promise;
		};
}]);

*/