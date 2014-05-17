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
 */

define([
	'app',
	'services/CoreService'
], function(app) {

	var rolesController = function ($scope, $http, $modal, toaster, CoreService) {

		var checkedRows = [];
		var roleEntities = [];

		var ModalInstanceCtrl = function ($scope, $modalInstance, data) {
			$scope.role = data.role;
			$scope.dialog = data.dialog;
			$scope.ok = function () {
				$modalInstance.close($scope.role);
			};
			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');
			};
		};

		$scope.addRole = function () {
			if (roleEntities.length == 0) {
				$scope.loadRoles();
			}
			var modalInstance = $modal.open({
				templateUrl: 'addRolesDlg.html',
				controller: ModalInstanceCtrl,
				resolve: {
					data : function() {
						return {
							role : {
								description : ""
							}, dialog : {
								title: "Create new Role"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (role) {
					CoreService.add("/roles", $scope, role).then(
						function(addedRole) {
							$scope.roleEntities.push(addedRole);
						}, function(e) {
							onError(e);
						}
					)
				}
			);
		};

		$scope.editRole = function (row) {
			var modalInstance = $modal.open({
				templateUrl: 'addRolesDlg.html',
				controller: ModalInstanceCtrl,
				resolve: {
					data: function () {
						return {
							role: $scope.roleEntities[row],
							dialog: {
								title: "Edit Role"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (role) {
					CoreService.save("/roles", $scope, role).then(
						rolesSaved, function(e) {
							onError(e);
						}
					)
				}
			);
		};

		$scope.deleteRole = function () {
			if ($scope.checkedRoles().length == 0) {
				return;
			}
			var param = "";
			angular.forEach($scope.checkedRoles(), function (role) {
				param+=role.name+",";
			});
			CoreService.delete('/roles/'+ param, $scope).then(
				function() {
					onSuccess("OK", "Successfully deleted selected Roles.");
					$scope.loadRoles();
				}, function(e) {
					onError(e);
				}
			);
		}

		$scope.saveRole = function () {

			/**
			 CoreService.save($scope).then(
				rolesSaved, function(data) {
					onError(data.items[0].httpStatus, data.items[0].message);
				}
			);
			 */
		}

		$scope.loadRoles = function () {
			checkedRows = [];
			CoreService.getAll("/roles", $scope).then(
				function(roles) {
					$scope.roleEntities = roles;
				}, function(e) {
					onError(e);
				}
			);
		}

		$scope.onRoleSelected = function (row) {
			$scope.selectedRole = $scope.roleEntities[row];
			$scope.page = 1;
			if ($scope.selectedRole.grants == undefined) {
				$scope.nextButton = {"enabled" : false};
				$scope.prevButton = {"enabled" : false};
			} else if ($scope.selectedRole.grants.length > 5) {
				$scope.nextButton = {"enabled" : true, "hidden" : false};
				$scope.prevButton = {"enabled" : true, "hidden" : true};
				$scope.grants = $scope.selectedRole.grants.slice(0, 5);
			} else {
				$scope.nextButton = {"enabled" : false};
				$scope.prevButton = {"enabled" : false};
				$scope.grants = $scope.selectedRole.grants;
			}
		}

		$scope.checkedRoles = function () {
			var result = [];
			angular.forEach(checkedRows, function (row) {
				result.push($scope.roleEntities[row]);
			});
			return result;
		}

		$scope.onRoleChecked = function (row) {
			var index = checkedRows.indexOf(row);
			if (index == -1) {
				// Not already selected
				checkedRows.push(row);
			} else {
				// remove row from selection
				checkedRows.splice(index, 1);
			}
		};

		$scope.roleStyleClass = function (row) {
			if (checkedRows.indexOf(row) == -1) {
				return "glyphicon glyphicon-unchecked";
			}
			return "glyphicon glyphicon-check";
		};

		$scope.previousGrantsPage = function() {
			$scope.page--;
			$scope.grants = $scope.selectedRole.grants.slice($scope.page*5, $scope.page*5+5);
			if ($scope.page == 1){
				$scope.grants = $scope.selectedRole.grants.slice($scope.page, $scope.page+5);
				$scope.prevButton = {"enabled" : true, "hidden" : true};
			}
			if ($scope.selectedRole.grants.length > 5) {
				$scope.grants = $scope.selectedRole.grants.slice($scope.page, $scope.page+5);
				$scope.nextButton = {"enabled" : true, "hidden" : false};
			}
		}

		$scope.nextGrantsPage = function() {
			console.log("role.grants for slice:"+$scope.selectedRole.grants.length);
			if ($scope.page*5+5 <= $scope.selectedRole.grants.length) {
				$scope.grants = $scope.selectedRole.grants.slice($scope.page*5, $scope.page*5+5);
				$scope.nextButton = {"enabled" : true, "hidden" : false};
			} else {
				$scope.grants = $scope.selectedRole.grants.slice($scope.page*5, $scope.selectedRole.grants.length+1);
				$scope.nextButton = {"enabled":false, "hidden":true};
			}
			$scope.page++;
			// Enable back button after a click on next...
			$scope.prevButton = {"enabled" : true, "hidden" : false};
		}

		var preLoad = function() {
			if ($scope.roleEntities === undefined) {
				$scope.loadRoles();
			}
		}();

		var rolesSaved = function(savedRole) {
			$scope.loadRoles();
			onSuccess("OK", "Saved successfully.");
		}
		var onError = function(e) {
			toaster.pop("error", "Server Error", "["+ e.data.httpStatus+"] "+ e.data.message);
		}
		var onSuccess = function(code, text) {
			toaster.pop("success", "Success", "["+code+"] "+text, 2000);
		}

	};

	app.register.controller('RolesController', ['$scope', '$http', '$modal', 'toaster', 'CoreService', rolesController]);
});

