'use strict';

/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/**
 * A RolesService provide CRUD functions for <code>Role</code> domain classes.
 *
 * @module openwms_services
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
var servicesModule = angular.module('openwms_services', ['ngResource']);

servicesModule.factory('rolesService',['$http', '$resource', '$q',
	function($http, $resource, $q) {
		return {
			add : function($scope, role) {
				console.log("Action: Add Role");
				var delay = $q.defer();
				$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
				$http.post($scope.rootUrl+'/roles', role)
					.success(function (addedRole) {
						delay.resolve(addedRole);
					})
					.error(function (status) {
						var msg = "Error ["+status+"] while saving a Role: ["+role.name+"]/["+role.description+"]";
						console.log(msg);
						//toaster.pop('error', "title", '<ul><li>Render html</li></ul>', null, 'trustedHtml');
						throw new Error(msg);
					});
				return delay.promise;
			},
			delete : function($scope, roles) {
				console.log("Action: Delete Role");
				var param = "";
				angular.forEach(roles, function (role) {
					param+=role.name+",";
				});
				var delay = $q.defer();
				$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
				$http.delete($scope.rootUrl+'/roles/'+ param)
					.success(function () {
						delay.resolve(roles);
					})
					.error(function (data, status) {
						var msg = "Error ["+status+"] while trying to delete Roles ["+roles+"]";
						console.log(msg);
						throw new Error(msg);
					});
				return delay.promise;
			},
			save : function ($scope, role) {
				console.log("Action: Save Role");
				var delay = $q.defer();
				$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
				$http.put($scope.rootUrl+'/roles', role)
					.success(function (savedRole) {
						angular.forEach($scope.roleEntities, function (role) {
							if (role.name == savedRole.name) {
								role = savedRole;
							}
						});
					})
					.error(function (data, status) {
						var msg = "Error ["+status+"] while saving a Role: ["+role.name+"]/["+role.description+"]";
						console.log(msg);
						throw new Error(msg);
					});
				return delay.promise;
			},
			getAll : function($scope) {
				console.log("Action: Get all Roles");
				var delay = $q.defer();
				$http.defaults.headers.common['Auth-Token'] = $scope.authToken;
				$http.get($scope.rootUrl+'/roles')
					.success(function (data) {
						delay.resolve(data);
					})
					.error(function (data, status) {
						var msg = "Error ["+status+"] while trying to read Roles";
						console.log(msg);
						throw new Error(msg);
					});
				return delay.promise;
			}
		}
	}
]);
