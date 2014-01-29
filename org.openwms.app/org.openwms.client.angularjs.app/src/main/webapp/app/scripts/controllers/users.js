'use strict';

angular.module('openwms_app')
	.config(function ($httpProvider,$compileProvider) {
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
		$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
	})
	.controller('UsersCtrl', function ($scope, $http, toaster) {


		$scope.addUser = function () {
			$http.defaults.headers.post['Auth-Token'] = $scope.authToken;
		}

		$scope.deleteUser = function () {
			console.log("deleteUser() selected");
			//$http.defaults.delete['Content-Type']='application/json';
			$http.delete($scope.rootUrl+'/users', $scope.selectedUser).success(function (data, status, headers, config) {
			});
			$scope.selectedUser = undefined;
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
			$http.get($scope.rootUrl+'/user').success(function (data, status, headers, config) {
				$scope.userEntities = data;
			});
		}

		$scope.selectedCard = function (row) {
			$scope.selectedUser = $scope.userEntities[row];
			console.log("Selected user:" + $scope.selectedUser.username);
		}
	});
