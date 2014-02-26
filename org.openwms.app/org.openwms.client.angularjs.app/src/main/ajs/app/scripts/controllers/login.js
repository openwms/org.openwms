'use strict';

/*
 * openwms.org, the Open Warehouse Management System.
 * Copyright (C) 2014 Heiko Scherrer
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as 
 * published by the Free Software Foundation; either version 2 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this software. If not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 * Main colors:
 * blue		: 2e7bb1
 * yellow	: e1e76b 
 * light-blue   : c9dcea
 * lighter-blue : edf4fa
 */

/**********************************************************************
 * Login controller
 **********************************************************************/
define(['angular'], function(angular) {
	'use strict';
	return angular.module('openwms.root').
	controller('LoginCtrl', function ($scope, $rootScope, $http, $location, coreService) {
	// This object will be filled by the form
	$scope.user = {};

	// Register the login() function
	$scope.login = function () {

		$scope.modal.opened = false;

		coreService.login($scope).then(
			function(data, status, headers, config) {
				if (data.status == 200) {
					$('#loginDialog').modal('hide');
					$rootScope.user = data.user;
					$rootScope.message = undefined;
					$rootScope.authToken = data.user.token;
					$location.url('/');
				} else {
					$rootScope.message = 'Authentication failed.';
					$rootScope.authToken = null;
					$scope.modal.opened = true;
				}
			}, function(e) {
				$rootScope.message = 'Authentication failed.';
				$rootScope.authToken = null;
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
});
});
