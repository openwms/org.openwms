'use strict';

angular.module('openwms_app', [])
	.controller('MainCtrl', ['$scope',
	function ($scope) {
		$scope.awesomeThings = [
			'HTML5 Boilerplate',
			'AngularJS',
			'MAIN'
		];
	}]).controller('NavigationCtrl', ['$scope',
	function ($scope) {
		$scope.users = {text:"Users"};
		$scope.linkActive;

		$scope.i18n = [
			{users:{ en:"Users", de:"Benutzer" }},
			{roles:{ en:"Roles", de:"Rollen" }}
		];

		$scope.clickCoreMenuItem = function () {
			$scope.users.text = "Benutzer";
			$scope.linkActive = 'users';
		}

	}]);
