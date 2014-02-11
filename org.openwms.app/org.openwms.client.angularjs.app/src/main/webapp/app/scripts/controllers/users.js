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
 *
 */
angular.module('openwms_users', ['ui.bootstrap', 'ngAnimate', 'toaster', 'angularFileUpload', 'base64'])
	.config(function ($httpProvider) {
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
		$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
	})
    .directive('chkUsers', [function() {
		return {
			restrict: 'A',
			link: function(scope, element, attrs, ngModel) {

				element.on('blur keyup change', function() {
					if (element.val().length > 2) {
						scope.$apply(read);
					}
				});
				read(scope); // initialize

				// Write data to the model
				function read(scope) {
					angular.forEach(scope.userEntities, function (user) {
						if (user.username.indexOf(element.val()) !== -1 ||
							user.fullname.indexOf(element.val()) !== -1 ||
							(user.userDetails != undefined && (user.userDetails.office.indexOf(element.val()) !== -1 ||
							user.userDetails.department.indexOf(element.val()) !== -1))) {
							console.log("Match:"+element.val());
						}
					});
				}
			}
    	}
	}])
	.controller('UsersCtrl', function ($scope, $http, $timeout, $modal, $upload, toaster, rolesService, $base64) {

		var selectedUsers = [];

		var ModalInstanceCtrl = function ($scope, $modalInstance, data) {
			$scope.selUser = data.selUser;
			$scope.dialog = data.dialog;
			$scope.ok = function () {
				$modalInstance.close($scope.selUser);
			};
			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');
			};
		};

		var UploadCtrl = function ($scope, $modalInstance, data) {
//			$scope.myModelObj = data.myModelObj;
			$scope.selectedUser = data.selectedUser;
			$scope.uploadDialog = data.dialog;
			$scope.ok = function () {
				$modalInstance.close($scope.selectedUser);
			};
			$scope.cancel = function () {
				$modalInstance.dismiss('cancel');
			};
			$scope.onFileSelect2 = function($files) {
				//$files: an array of files selected, each file has name, size, and type.
				for (var i = 0; i < $files.length; i++) {
					var file = $files[i];
					$scope.upload = $upload.upload({
						url: $scope.rootUrl+"/users/"+$scope.selectedUser.id,//'server/upload/url', //upload.php script, node.js route, or servlet url
						method: 'PUT',
						headers: {'Content-Type': 'multipart/form-data'},
						// withCredential: true,
						data: {myModelObj: $scope.myModelObj},
						file: file
						// file: $files, //upload multiple files, this feature only works in HTML5 FromData browsers
						/* set file formData name for 'Content-Desposition' header. Default: 'file' */
						//fileFormDataName: myFile, //OR for HTML5 multiple upload only a list: ['name1', 'name2', ...]
						/* customize how data is added to formData. See #40#issuecomment-28612000 for example */
						//formDataAppender: function(formData, key, val){} //#40#issuecomment-28612000
					}).progress(function(evt) {
							console.log('percent: ' + parseInt(100.0 * evt.loaded / evt.total));
						}).success(function(data, status, headers, config) {
							// file is uploaded successfully
							console.log(data);
						});
					//.error(...)
					//.then(success, error, progress);
				}
			};



			$scope.fileReaderSupported = window.FileReader != null;
			$scope.uploadRightAway = true;
			$scope.hasUploader = function(index) {
				return $scope.upload[index] != null;
			};
			$scope.abort = function(index) {
				$scope.upload[index].abort();
				$scope.upload[index] = null;
			};
			$scope.onFileSelect = function($files) {
				$scope.selectedFiles = [];
				$scope.progress = [];
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
				for ( var i = 0; i < $files.length; i++) {
					var $file = $files[i];
					if (window.FileReader && $file.type.indexOf('image') > -1) {
						var fileReader = new FileReader();
						fileReader.readAsDataURL($files[i]);
						/*function setPreview(fileReader, index) {
							fileReader.onload = function(e) {
								$timeout(function() {
									$scope.dataUrls[i] = e.target.result;
								});
							}
						}*/
						//setPreview(fileReader, i);
					}
					$scope.progress[i] = -1;
					if ($scope.uploadRightAway) {
						$scope.start(i, $files[i]);
					}
				}
			}

			$scope.start = function(index, file) {
				$scope.progress[index] = 0;
				if (11 == 1) {
					$scope.upload[index] = $upload.upload({
						url : $scope.rootUrl+"/users/"+$scope.selectedUser.id,
						method: 'PUT',
						headers: {
							'Content-Type': 'multipart/form-data'/*,
							'X-File-Name': file.fileName,
							'X-File-Size': file.fileSize,
							'X-File-Type': file.type*/
						},
						data : {
							myModelObj : file
						},
						/* formDataAppender: function(fd, key, val) {
						 if (angular.isArray(val)) {
						 angular.forEach(val, function(v) {
						 fd.append(key, v);
						 });
						 } else {
						 fd.append(key, val);
						 }
						 }, */
						file: $scope.selectedFiles[index],
						fileFormDataName: 'myFile'
					}).then(function(response) {
							$scope.uploadResult.push(response.data);
						}, null, function(evt) {
							$scope.progress[index] = parseInt(100.0 * evt.loaded / evt.total);
						});
				} else {
					var fileReader = new FileReader();
					fileReader.readAsDataURL($scope.selectedFiles[index]);
					fileReader.onload = function(e) {
						$scope.upload[index] = $upload.http({
							url: $scope.rootUrl+"/users/"+$scope.selectedUser.id,
							method: 'PUT',
							headers: {'Content-Type': 'multipart/form-data'},//$scope.selectedFiles[index].type},
							data: e.target.result
						}).then(function(response) {
								$scope.uploadResult.push(response.data.result);
							}, null, function(evt) {
								// Math.min is to fix IE which reports 200% sometimes
								$scope.progress[index] = Math.min(100, parseInt(100.0 * evt.loaded / evt.total));
							});
					}
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
								userDetails : ""
							}, dialog : {
								title: "Add a new User"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (user) {
					rolesService.add("/users", $scope, user).then(
						function(addedEntity) {
							$scope.userEntities.push(addedEntity);
						},
						function(data) {
							toaster.pop('error', "Server Error","["+data.items[0].httpStatus+"] "+data.items[0].message);
						}
					)
				}
			);
		}

		$scope.editUser = function () {
			var modalInstance = $modal.open({
				templateUrl: 'addUserDlg.html',
				controller: ModalInstanceCtrl,
				resolve: {
					data : function() {
						return {
							selUser : $scope.selectedUser,
							dialog : {
								title: "Edit User"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (user) {
					rolesService.save("/users", $scope, user).then(
						onSaved, function(data) {
							onError(data.items[0].httpStatus, data.items[0].message);
						}
					)
				}
			);
		}

		$scope.deleteUser = function () {
			if (selectedUsers === undefined || selectedUsers.length == 0) {
				return;
			}
			var param = "";
			angular.forEach(selectedUsers, function (row) {
				param+=$scope.userEntities[row].username+",";
			});
			rolesService.delete('/users/'+ param, $scope).then(
				function() {
					onSuccess("OK", "Successfully deleted selected Users.");
					$scope.loadUsers();
				}, function(data) {
					toaster.pop("error", "Server Error", "["+data.items[0].httpStatus+"] "+data.items[0].message);
				}
			);
			selectedUsers = [];
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

		$scope.loadUsers = function () {
			$http.defaults.headers.common['Auth-Token'] = $scope.authToken;
			$http.get($scope.rootUrl+'/users').success(function (data, status, headers, config) {
				$scope.userEntities = data;
			});
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
							selectedUser : $scope.selectedUser,
							dialog : {
								title: "Upload an Image"
							}
						};
					}
				}
			});
			modalInstance.result.then(
				function (file) {
					rolesService.add("/users", $scope, file).then(
						function(addedEntity) {
							$scope.userEntities.push(addedEntity);
						},
						function(data) {
							toaster.pop('error', "Server Error","["+data.items[0].httpStatus+"] "+data.items[0].message);
						}
					)
				}
			);
		}

		/**
		 * Mark the User with the <tt>row</tt> index as selected or deselected.
		 *
		 * @param row The current User's row index
		 */
		$scope.onClickUserCard = function (row) {
			if ($scope.isSelected(row)) {
				// remove row from selection array
				var index = selectedUsers.indexOf(row);
				selectedUsers.splice(index, 1);
				$scope.selectedUser = undefined;
			} else {
				// Not already selected, so select this user
				selectedUsers.push(row);
				$scope.selectedUser = $scope.userEntities[row];
			}
		}

		/**
		 * Check whether the User with index is in the collection of selected users.
		 * @param index
		 * @returns {boolean}
		 */
		$scope.isSelected = function (index) {
			return selectedUsers.indexOf(index) >= 0 ? true : false;
		}

		$scope.multipleSelected = function () {
			if (selectedUsers !== undefined && selectedUsers.length > 1) {
				return true;
			}
			return false;
		}
		$scope.oneSelected = function () {
			if (selectedUsers !== undefined && selectedUsers.length == 1) {
				return true;
			}
			return false;
		}


		var preLoad = function() {
			if ($scope.userEntities === undefined) {
				$scope.loadUsers();
			}
		}
		var init = preLoad();

		var onSaved = function() {
			$scope.loadUsers();
			onSuccess("OK", "Saved successfully.");
		}
		var onError = function(code, text) {
			toaster.pop("error", "Server Error", "["+code+"] "+text);
		}
		var onSuccess = function(code, text) {
			toaster.pop("success", "Success", "["+code+"] "+text, 2000);
		}


	});
