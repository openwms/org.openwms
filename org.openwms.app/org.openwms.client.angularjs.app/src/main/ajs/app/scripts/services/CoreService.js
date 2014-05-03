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
 */

define(['app'], function (app) {

	var srv = function ($http, $q) {

		var coreServiceFactory = {};

		coreServiceFactory.add = function (url, $scope, entity) {
			var delay = $q.defer();
			$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
			$http.post($scope.rootUrl + url, entity)
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

		coreServiceFactory.delete = function (url, $scope) {
			var delay = $q.defer();
			$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
			$http.delete($scope.rootUrl + url)
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
			$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
			$http.defaults.headers.common['Content-Type'] = 'application/json';
			$http.put($scope.rootUrl + url, entity)
				.success(function (savedEntity) {
					delay.resolve(savedEntity);
				})
				.error(function (data, status, headers, config) {
					delay.reject(new Error(status, config));
				});
			return delay.promise;
		};

		coreServiceFactory.getAll = function (url, $scope) {
			var delay = $q.defer();
			$http.defaults.headers.common['Auth-Token'] = $scope.authToken;
			$http.get($scope.rootUrl + url)
				.success(function (data) {
					delay.resolve(data.items[0].obj[0]);
				})
				.error(function (data, status, headers, config) {
					delay.reject(new Error(status, config));
				});
			return delay.promise;
		};

		coreServiceFactory.login = function ($scope) {
			var delay = $q.defer();
			$http.defaults.headers.post['Auth-Token'] = $scope.authToken;
			$http.post($scope.rootUrl + '/sec/login', {
				username: $scope.user.username,
				password: $scope.user.password
			})
				.success(function (data, status, headers, config) {
					delay.resolve({user: data, status: status});
				})
				.error(function (data, status, headers, config) {
					delay.reject(data);
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
		}

		return coreServiceFactory;

	};

	app.factory('CoreService', ['$http', '$q', srv]);

});