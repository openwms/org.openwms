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

/**
 * A coreService provide CRUD functions to domain classes of the CORE module.
 *
 * @module core_service
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
var servicesModule = angular.module('core_service', []);

servicesModule.factory('coreService',['$http', '$q',
	function($http, $q) {
		return {
			/**
			 * Create a non-existing Role instance and send an POST request to the backend. If the Role with its unique identifier exists the service will reject the request.
			 *
			 * @param url The part of the URL that defines the type of entities to work with
			 * @param $scope The current scope
			 * @param entity the entity to add
			 * @returns {Promise.promise|*} A promise to evaluate: In case of success the Role is returned, otherwise the ResponseItem
			 */
			add : function(url, $scope, entity) {
				var delay = $q.defer();
				$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
				$http.post($scope.rootUrl+url, entity)
					.success(function (data) {
						delay.resolve(data.items[0].obj[0]);
					})
					.error(function (data, status, headers, config) {
						console.log("HHHH");
						var err = new Error(status, data);
						err.data = {
							httpStatus: data.items[0].httpStatus,
							message: data.items[0].message
						};
						delay.reject(err);
					});
				return delay.promise;
			},
			/**
			 * Send a http DELETE request to remove selected Roles. The name of Roles to delete are appended as URL request parameter.
			 *
			 * @param url The part of the URL that defines the type of entities to work with
			 * @param $scope The current scope
			 * @returns {Promise.promise|*} A promise to evaluate: In case of success nothing is returned, otherwise the ResponseItem
			 */
			delete : function(url, $scope) {
				var delay = $q.defer();
				$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
				$http.delete($scope.rootUrl+url)
					.success(function () {
						delay.resolve();
					})
					.error(function (data, status, headers, config) {
						delay.reject(new Error(status, config));
					});
				return delay.promise;
			},
			/**
			 * Send a http PUT request to save a Role. The Role to save must already exist.
			 *
			 * @param url The part of the URL that defines the type of entities to work with
			 * @param $scope The current scope
			 * @param entity The existing Entity to update
			 * @returns {Promise.promise|*} A promise to evaluate: In case of success the updated Role is returned, otherwise the ResponseItem
			 */
			save : function (url, $scope, entity) {
				var delay = $q.defer();
				$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
				$http.defaults.headers.common['Content-Type'] = 'application/json';
				$http.put($scope.rootUrl+url, entity)
					.success(function (savedEntity) {
						delay.resolve(savedEntity);
					})
					.error(function (data, status, headers, config) {
						delay.reject(new Error(status, config));
					});
				return delay.promise;
			},
			/**
			 * Send a http GET request to load and retrieve all existing entities from the backend.
			 *
			 * @param url The part of the URL that defines the type of entities to work with
			 * @param $scope The current scope
			 * @returns {Promise.promise|*} A promise to evaluate: In case of success all retrieved entities are returned, otherwise the ResponseItem
			 */
			getAll : function(url, $scope) {
				var delay = $q.defer();
				$http.defaults.headers.common['Auth-Token'] = $scope.authToken;
				$http.get($scope.rootUrl+url)
					.success(function (data) {
						delay.resolve(data.items[0].obj[0]);
					})
					.error(function (data, status, headers, config) {
						delay.reject(new Error(status, config));
					});
				return delay.promise;
			}
		}
	}
]);
