'use strict';

var services = angular.module('openwms.services', ['ngResource']);

services.factory('UsersService', ['$resource',
	function($resource) {
		return $resource('/user/:query', {query: '@query'});
	}]);

services.factory('GetUsers', ['UsersService', '$q',
	function(UsersService, $q) {
		return function() {
			var delay = $q.defer();
			UsersService.query(function(users) {
				delay.resolve(users);
			}, function() {
				delay.reject('Unable to fetch recipes');
			});
			return delay.promise;
		};
	}]);

services.factory('RecipeLoader', ['Recipe', '$route', '$q',
	function(Recipe, $route, $q) {
		return function() {
			var delay = $q.defer();
			Recipe.get({id: $route.current.params.recipeId}, function(recipe) {
				delay.resolve(recipe);
