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

define([
 'angular'/*,
 'angular_ui_router',
 'ui_bootstrap',
 'ui_bootstrap_tpls'*/
], function () {

	/*
	 var routeResolver = function () {

	 this.$get = function () {
	 return this;
	 };

	 this.routeConfig = function () {
	 var viewsDirectory = '/views/',
	 controllersDirectory = '/scripts/controllers/',

	 setBaseDirectories = function (viewsDir, controllersDir) {
	 viewsDirectory = viewsDir;
	 controllersDirectory = controllersDir;
	 },

	 getViewsDirectory = function () {
	 return viewsDirectory;
	 },

	 getControllersDirectory = function () {
	 return controllersDirectory;
	 };

	 return {
	 setBaseDirectories: setBaseDirectories,
	 getControllersDirectory: getControllersDirectory,
	 getViewsDirectory: getViewsDirectory
	 };
	 }();

	 this.route = function (routeConfig) {

	 var resolve = function (baseName, url, path) {
	 if (!path) path = '';
	 var routeDef = {};
	 var injector = angular.injector(["ng"]);

	 //routeDef.templateUrl = routeConfig.getViewsDirectory() + path + baseName + '.html';

	 var $q = injector.get("$q");
	 var deferred = $q.defer();

	 require(['controllers/'+baseName + 'Controller'], function (controllerRef) {
	 var app = angular.module('app');
	 app.register.controller('UsersController', ['CoreService', controllerRef]);
	 myFunction(controllerRef, routeDef);
	 //$rootScope.$apply(function () {
	 //	deferred.resolve();
	 //});
	 });

	 //var controllerProvider = injector.get("$controller")('TestCtrl', { $scope: {} });;
	 //controllerProvider.register(baseName + 'Controller', deferred.promise);
	 function myFunction(controller, routeDef) {
	 routeDef.controller = controller;//deferred.promise;//$injector.get(baseName + 'Controller');
	 routeDef.resolve = {
	 load: ['$q', '$rootScope', function ($q, $rootScope) {
	 var dependencies = [routeConfig.getControllersDirectory() + path + baseName + 'Controller.js'];
	 return resolveDependencies($q, $rootScope, dependencies);
	 }]
	 };
	 }
	 return routeDef;
	 },

	 resolveDependencies = function ($q, $rootScope, dependencies) {
	 var defer = $q.defer();
	 require(dependencies, function () {
	 defer.resolve();
	 $rootScope.$apply()
	 });

	 return defer.promise;
	 };

	 return {
	 templateUrl: "views/partials/default-menu.html",
	 controller: 'DefaultNavigationController'
	 };
	 }//(this.routeConfig);

	 this.route = function (routeConfig) {
	 function resolve(baseName, path, callback) {
	 require(['services/CoreService', 'controllers/' + baseName + 'Controller'], function (controllerRef) {
	 var app = angular.module('app');
	 app.register.controller(baseName + 'Controller', ['CoreService', controllerRef]);

	 callback({
	 templateUrl: routeConfig.getViewsDirectory() + baseName + '.html',
	 controller: baseName + 'Controller'
	 });
	 });
	 }

	 return {
	 resolve: resolve
	 }
	 }(this.routeConfig);
	 };

	 */


	var routeResolver = function () {

		this.$get = function () {
			return this;
		};

		this.routeConfig = function () {
			var viewsDirectory = '/views/',
				controllersDirectory = '/scripts/controllers/',

				setBaseDirectories = function (viewsDir, controllersDir) {
					viewsDirectory = viewsDir;
					controllersDirectory = controllersDir;
				},

				getViewsDirectory = function () {
					return viewsDirectory;
				},

				getControllersDirectory = function () {
					return controllersDirectory;
				};

			return {
				setBaseDirectories: setBaseDirectories,
				getControllersDirectory: getControllersDirectory,
				getViewsDirectory: getViewsDirectory
			};
		}();

		this.route = function (routeConfig) {

			var resolve = function (baseName, path) {
					if (!path) path = '';

					var routeDef = {};
					routeDef.templateUrl = routeConfig.getViewsDirectory() + path + baseName + '.html';
					routeDef.controller = baseName + 'Controller';
					routeDef.resolve = {
						load: ['$q', '$rootScope', function ($q, $rootScope) {
							var dependencies = [routeConfig.getControllersDirectory() + path + baseName + 'Controller.js'];
							return resolveDependencies($q, $rootScope, dependencies);
						}]
					};

					return routeDef;
				},

				resolveDependencies = function ($q, $rootScope, dependencies) {
					var defer = $q.defer();
					require(dependencies, function () {
						defer.resolve();
						$rootScope.$apply()
					});

					return defer.promise;
				};

			return {
				resolve: resolve
			}
		}(this.routeConfig);

	};

	var servicesApp = angular.module('routeResolverServices', []);

	//Must be a provider since it will be injected into module.config()
	servicesApp.provider('routeResolver', routeResolver);
});