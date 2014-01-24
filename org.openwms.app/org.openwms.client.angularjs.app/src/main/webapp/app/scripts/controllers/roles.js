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
 * A RolesCtrl backes the 'Roles Management' screen.
 *
 * @module openwms_app
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
angular.module('openwms_app',['ui.bootstrap'])
	.config(function ($httpProvider) {
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
		$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
	})
	.controller('RolesCtrl', function ($scope, $http, $modal, $log, rolesService) {

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
					rolesService.add($scope, role).then(function(addedRole) {
						$scope.roleEntities.push(addedRole);
				})},
				function () {
					$log.info('Modal dismissed at: ' + new Date());
				}
			);
		};

		$scope.editRole = function (row) {
			var modalInstance;
			modalInstance = $modal.open({
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
					rolesService.save($scope, role).then(function(savedRole) {
						$scope.roleEntities.push(savedRole);
					})},
				function () {
					$log.info('Modal dismissed at: ' + new Date());
				}
			);
		};

		$scope.deleteRole = function () {
			rolesService.delete($scope)
				.then(function(deletedRoleName) {
					angular.forEach($scope.roleEntities, function (role, i) {
						if (role.name == deletedRoleName) {
							$scope.roleEntities.splice(i, 1);
							$scope.selectedRole = undefined;
						}
					});
				});
		}

		$scope.saveRole = function () {
			rolesService.save($scope);
		}

		$scope.loadRoles = function () {
			rolesService.getAll($scope).then(function(roles) {
				$scope.roleEntities = roles;
			});
		}

		$scope.onRoleSelected = function (row) {
			$scope.selectedRole = $scope.roleEntities[row];
			$scope.page = 1;
			if ($scope.selectedRole.grants.length > 5) {
				$scope.nextButton = {"enabled" : true, "hidden" : false};
				$scope.prevButton = {"enabled" : true, "hidden" : true};
				$scope.grants = $scope.selectedRole.grants.slice(0, 5);
			} else {
				$scope.nextButton = {"enabled" : false};
				$scope.prevButton = {"enabled" : false};
				$scope.grants = $scope.selectedRole.grants;
			}
		}

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
	});

