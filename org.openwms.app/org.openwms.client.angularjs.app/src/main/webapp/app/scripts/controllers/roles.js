'use strict';

angular.module('openwms_app')
	.config(function ($httpProvider) {
		delete $httpProvider.defaults.headers.common['X-Requested-With'];
		$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';
	})
	.controller('RolesCtrl', function ($scope, $http) {
		function RolesCtrl($scope, $http) {
			loadUsers();
		}

		$scope.addRole = function () {
			$http.defaults.headers.post['Auth-Token'] = $scope.authToken;
		}

		$scope.deleteRole = function () {
			console.log("deleteRole() selected");
			//$http.defaults.delete['Content-Type']='application/json';
			$http.delete($scope.rootUrl+'/role', $scope.selectedRole).success(function (data, status, headers, config) {});
			$scope.selectedRole = undefined;
		}

		$scope.saveRole = function () {
			$http.defaults.headers.put['Auth-Token'] = $scope.authToken;
			$http.put($scope.rootUrl+'/role', $scope.selectedRole).success(function (data, status, headers, config) {
				$scope.selectedRole = data;
				angular.forEach($scope.roleEntities, function (role) {
					if (role.name == $scope.selectedRole.name) {
						role = $scope.selectedRole;
					}
				});
			});
		}

		$scope.loadRoles = function () {
			$http.defaults.headers.common['Auth-Token'] = $scope.authToken;
			$http.get($scope.rootUrl+'/role').success(function (data, status, headers, config) {
				$scope.roleEntities = data;
			});
		}

		$scope.onRoleSelected = function (row) {
			$scope.selectedRole = $scope.roleEntities[row];
			$scope.page = 1;
			if ($scope.selectedRole.grants.length > 5) {
				$scope.nextButton = {"enabled":true, "hidden":false};
				$scope.prevButton = {"enabled":true, "hidden":true};
				$scope.grants = $scope.selectedRole.grants.slice(0,5);
			} else {
				$scope.nextButton = {"enabled":false};
				$scope.prevButton = {"enabled":false};
				$scope.grants = $scope.selectedRole.grants;
			}
		}

		$scope.previousGrantsPage = function() {
			$scope.page--;
			$scope.grants = $scope.selectedRole.grants.slice($scope.page*5+1, $scope.page*5+1+5);
			if ($scope.page == 1){
				$scope.prevButton = {"enabled":true, "hidden":true};
			}
			if ($scope.selectedRole.grants.length > 5) {
				$scope.nextButton = {"enabled":true, "hidden":false};
			}
		}

		$scope.nextGrantsPage = function() {
			if ($scope.page*5+1+5 <= $scope.selectedRole.grants.length) {
				$scope.grants = $scope.selectedRole.grants.slice($scope.page*5+1, $scope.page*5+1+5);
				$scope.nextButton = {"enabled":true, "hidden":false};
			} else {
				$scope.grants = $scope.selectedRole.grants.slice($scope.page*5+1, $scope.selectedRole.grants.length+1);
				$scope.nextButton = {"enabled":false, "hidden":true};
			}
			$scope.page++;
			// Enable back button after a click on next...
			$scope.prevButton = {"enabled":true, "hidden":false};
		}
	});
