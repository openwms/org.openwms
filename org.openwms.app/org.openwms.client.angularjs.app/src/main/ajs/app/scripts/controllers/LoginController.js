'use strict';

define([
	'app',
	'services/CoreService'
], function (app) {

	var loginController = function ($scope, $rootScope, $http, $location, $window, CoreService) {
		// This object will be filled by the form
		$scope.user = {};

		// Register the login() function
		$scope.login = function () {

			$scope.modal.opened = false;

			CoreService.login($scope).then(
				function (data, status, headers, config) {
					if (data.status === 200) {
						$('#loginDialog').modal('hide');
						$rootScope.user = data.user;
						$rootScope.message = undefined;
						$rootScope.authToken = data.user.token;
						$window.sessionStorage.setItem('aToken', data.user.token);
						$location.url('/');
					} else {
						$rootScope.message = 'Authentication failed.';
						$window.sessionStorage.removeItem('aToken');
						delete $rootScope.authToken;
						$scope.modal.opened = true;
					}
				}, function (e) {
					$rootScope.message = 'Authentication failed.';
					$window.sessionStorage.removeItem('aToken');
					delete $rootScope.authToken;
					$scope.modal.opened = true;
				}
			);


			return;


			$http.defaults.headers.post['Auth-Token'] = $rootScope.authToken;
			$http.post($rootScope.rootUrl + '/sec/login', {
				username: $scope.user.username,
				password: $scope.user.password
			})
			.success(function (user) {
				$rootScope.user = user;
				$rootScope.message = undefined;
				$rootScope.authToken = user.token;
				$location.url('/');
				$('#loginDialog').modal('hide');
				//$('#loginDialog').modal('hide');
				//$scope.modal.opened = false;
			})
			.error(function (data, status, headers, config) {
				$rootScope.message = 'Authentication failed.';
				$rootScope.authToken = null;
				$scope.modal.opened = true;
//				$('#loginDialog').modal('show');
				//$location.url('/login').replace();
			});
		};
	};

	app.register.controller('LoginController', ['$scope', '$rootScope', '$http', '$location', '$window', 'CoreService', loginController]);
});
