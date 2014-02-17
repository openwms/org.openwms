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
 *
 */
angular.module('openwms_users', ['ui.bootstrap', 'ngAnimate', 'toaster', 'angularFileUpload', 'base64'])


    .directive('chkUsers', [function() {
		return {
			restrict: 'A',
			link: function(scope, element, attrs, ngModel) {

				element.on('blur keyup change', function() {
					if (element.val().length > 0) {
						scope.$apply(scope.selectedUsers = []);
						scope.$apply(read);
					} else if (element.val().length == 0) {
						scope.$apply(scope.selectedUsers = []);
					}
				});
				read(scope); // initialize

				// Write data to the model
				function read(scope) {
					angular.forEach(scope.userEntities, function (user) {
						if (user.username.toUpperCase().indexOf(element.val().toUpperCase()) !== -1 ||
							user.fullname.toUpperCase().indexOf(element.val().toUpperCase()) !== -1 ||
							(user.userDetails != undefined && (user.userDetails.office.toUpperCase().indexOf(element.val().toUpperCase()) !== -1 ||
							user.userDetails.department.toUpperCase().indexOf(element.val().toUpperCase()) !== -1))) {
							scope.selectedUsers.push(user);
						}
					});
				}
			}
    	}
	}])


	.controller('UsersCtrl', function ($scope, $http, $timeout, $modal, $upload, toaster, coreService, $base64) {

		$scope.selectedUsers = [];

		/**
		 * 'Modify User' dialogue.
		 *
		 * @param $scope
		 * @param $modalInstance
		 * @param data
		 * @constructor
		 */
		var ModalInstanceCtrl = function ($scope, $modalInstance, data) {
			$scope.selUser = data.selUser;
			$scope.dialog = data.dialog;
			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');
			};
			$scope.ok = function () {
				$modalInstance.close($scope.selUser);
			}
		};

		/**
		 * 'Upload Image' dialogue.
		 *
		 * @param $scope
		 * @param $modalInstance
		 * @param data
		 * @constructor
		 */
		var UploadCtrl = function ($scope, $modalInstance, data) {
			$scope.selectedUsers = data.selectedUsers;
			$scope.uploadDialog = data.dialog;
			$scope.ok = function () {
				$modalInstance.close($scope.selectedUsers);
			};
			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');
			};
			$scope.hasUploader = function(index) {
				return $scope.upload[index] != null;
			};
			$scope.abort = function(index) {
				$scope.upload[index].abort();
				$scope.upload[index] = null;
			};
			$scope.onFileSelect = function($files) {
				$scope.selectedFiles = [];
				$scope.progress = 0;
				if ($scope.upload && $scope.upload.length > 0) {
					for (var i = 0; i < $scope.upload.length; i++) {
						if ($scope.upload[i] != null) {
							$scope.upload[i].abort();
						}
					}
				}
				$scope.upload = [];
				$scope.uploadResult = [];
				$scope.selectedFiles = $files;
				$scope.dataUrls = [];
				if ($files.length == 1) {
					var $file = $files[0];
					if (window.FileReader && $file.type.indexOf('image') > -1) {
						var fileReader = new FileReader();
						fileReader.readAsDataURL($file);
					}
					$scope.progress = -1;
				}
			}

			$scope.start = function() {
				$scope.progress = 0;
				var fileReader = new FileReader();
				fileReader.readAsDataURL($scope.selectedFiles[0]);
				fileReader.onload = function(e) {
					$scope.upload[0] = $upload.http({
						url: $scope.rootUrl+"/users/"+$scope.selectedUsers[0].id,
						method: 'PUT',
						headers: {'Content-Type': 'multipart/form-data'},
						data: e.target.result
					}).then(function(response) {
							$scope.uploadResult.push(response.data.result);
						}, null, function(evt) {
							// Math.min is to fix IE which reports 200% sometimes
							$scope.progress = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
						});
				}
			};
		};

		$scope.decode = function(img) {
			return $base64.decode(img);
		}

		$scope.addUser = function () {
			var modalInstance = $modal.open({
				templateUrl: 'addUserDlg.html',
				controller: ModalInstanceCtrl,
				resolve: {
					data : function() {
						return {
							selUser : {
								userDetails : undefined
							}, dialog : {
								title: "Add a new User"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (user) {
					coreService.add("/users", $scope, user).then(
						function(addedEntity) {
							$scope.userEntities.push(addedEntity);
						}, function(e) {
							onError(e);
						}
					)
				}
			);
		}

		/**
		 * Edit User function.
		 */
		$scope.editUser = function () {
			var modalInstance = $modal.open({
				templateUrl: 'addUserDlg.html',
				controller: ModalInstanceCtrl,
				resolve: {
					data : function() {
						return {
							selUser : $scope.selectedUser(),
							dialog : {
								title: "Edit User"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (user) {
					coreService.save("/users", $scope, user).then(
						onSaved, function(e) {
							onError(e);
						}
					)
				}
			);
		}

		/**
		 * Delete existing user function.
		 */
		$scope.deleteUser = function () {
			if ($scope.selectedUsers === null || $scope.selectedUsers.length == 0) {
				return;
			}
			var param = "";
			angular.forEach($scope.selectedUsers, function (user) {
				param+=user.username+",";
			});
			coreService.delete('/users/'+ param, $scope).then(
				function() {
					onSuccess("OK", "Success", "Successfully deleted selected Users.");
					$scope.loadUsers();
				}, function(e) {
					onError(e);
				}
			);
		}

		$scope.saveUser = function () {
			$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
			$http.put($scope.rootUrl+'/users', $scope.selectedUser).success(function (data, status, headers, config) {
				$scope.selectedUser = data;
				angular.forEach($scope.userEntities, function (user) {
					if (user.username == $scope.selectedUser.username) {
						user = $scope.selectedUser;
						toaster.pop('success', "", "User saved");
					}
				});
			});
		}

		/**
		 * Load all existing Users function. Result is written to userEntities.
		 */
		$scope.loadUsers = function () {
			$scope.selectedUsers = [];
			coreService.getAll("/users", $scope).then(
				function(users) {
					$scope.userEntities = users;
				}, function(e) {
					onError(e);
				}
			);
		}

		$scope.changePassword = function () {
			$http.get($scope.rootUrl+'/users').success(function (data, status, headers, config) {
				$scope.userEntities = data;
			});
		}

		$scope.changeImage = function () {
			var modalInstance = $modal.open({
				templateUrl: 'uploadDlg.html',
				controller: UploadCtrl,
				resolve: {
					data : function() {
						return {
							selectedUsers : $scope.selectedUsers,
							dialog : {
								title: "Upload an Image"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (file) {

				}, function() {
					$scope.loadUsers();
				}
			);
		}

		/**
		 * Mark the User with the <tt>row</tt> index as selected or deselected.
		 *
		 * @param index The current User's row index
		 */
		$scope.onClickUserCard = function (index) {
			if ($scope.isSelected(index)) {
				// remove row from selection array
				var i = $scope.selectedUsers.indexOf($scope.userEntities[index]);
				$scope.selectedUsers.splice(i, 1);
			} else {
				// Not selected, so select this user
				$scope.selectedUsers.push($scope.userEntities[index]);
			}
		}

		/**
		 * Returns the latest selected User.
		 *
		 * @returns {User}
		 */
		$scope.selectedUser = function() {
			return $scope.selectedUsers[$scope.selectedUsers.length-1];
		}

		/**
		 * Check whether the User with index is in the collection of selected users.
		 *
		 * @param index
		 * @returns {boolean}
		 */
		$scope.isSelected = function (index) {
			return $scope.selectedUsers.indexOf($scope.userEntities[index]) > -1 ? true : false;
		}
		/**
		 * Returns true if more than one User is selected, otherwise false.
		 *
		 * @returns {boolean}
		 */
		$scope.multipleSelected = function () {
			return $scope.selectedUsers.length > 1 ? true : false;
		}
		/**
		 * Returns true if only one User is selected, otherwise false.
		 *
		 * @returns {boolean}
		 */
		$scope.oneSelected = function () {
			return $scope.selectedUsers.length == 1 ? true : false;
		}
		/**
		 * On view load, all Users are loaded, if not already loaded before.
		 */
		var preLoad = function() {
			if ($scope.userEntities === undefined) {
				$scope.loadUsers();
			}
		}
		var init = preLoad();
		/**
		 * Load users and toast success.
		 */
		var onSaved = function() {
			$scope.loadUsers();
			onSuccess("OK", "Success", "Saved successfully.");
		}
		/**
		 * Toast an error.
		 *
		 * @param e e.data.httpStatus expected to hold the http response status, e.data.message a message text
		 */
		var onError = function(e) {
			toaster.pop("error", "Server Error", "["+ e.data.httpStatus+"] "+ e.data.message);
		}
		/**
		 * Toast success.
		 *
		 * @param code a message code
		 * @param header a header text
		 * @param text a message text
		 */
		var onSuccess = function(code, header, text) {
			toaster.pop("success", header, "["+code+"] "+text, 2000);
		}
	});
