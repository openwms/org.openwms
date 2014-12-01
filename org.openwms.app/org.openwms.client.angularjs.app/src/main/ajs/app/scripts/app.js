'use strict';

define([
	'angular',
	'exports',
	'radio',
	'require',
	'jquery',
	'ui_bootstrap',
	'ui_bootstrap_tpls',
	'angular_ui_router',
	'angular_local_storage',
	'angular_resource',
	'angular_translate',
	'angular_translate_lp',
	'underscore',
	'core_rtModel',
	'core_secModel',
	'core_envModel',
	'routeResolver',
  'blueimp',
], function () {

	var app = angular.module('app', [
    'ui.router',
    'pascalprecht.translate',
    'xeditable',
    'rtModelModule',
    'secModelModule',
    'coreEnvModel',
    'LocalStorageModule',
    'routeResolverServices',
    'ngResource',
    'ui.bootstrap',
    'angularFileUpload',
    'toaster',
    'base64'
  ]);

	app.config(['routeResolverProvider', '$translateProvider', '$translatePartialLoaderProvider', 'RTConfig', 'SecurityConfig', '$controllerProvider', '$compileProvider', '$filterProvider', '$provide', '$stateProvider', '$urlRouterProvider', '$locationProvider', '$httpProvider',
		function (routeResolverProvider,$translateProvider, $translatePartialLoaderProvider, RTConfig, SecurityConfig, $controllerProvider, $compileProvider, $filterProvider, $provide, $stateProvider, $urlRouterProvider, $locationProvider, $httpProvider) {

			$translatePartialLoaderProvider.addPart('base');
			$translateProvider.useLoader('$translatePartialLoader', {
				urlTemplate: '/i18n/{part}/{lang}.json'
			});
			$translateProvider.preferredLanguage('en_US');
			$translateProvider.preferredLanguage('de_DE');
			$translateProvider.determinePreferredLanguage();

			app.register =
			{
				controller: $controllerProvider.register,
				directive: $compileProvider.directive,
				filter: $filterProvider.register,
				factory: $provide.factory,
				service: $provide.service
			};
			var route = routeResolverProvider.route;

			$compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|data):/);

			// todo why? delete $httpProvider.defaults.headers.common['X-Requested-With'];
			$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';

			var checkLoggedin = function ($q, $timeout, $http, $location, $rootScope) {
				// Initialize a new promise
				var deferred = $q.defer();
				delete $http.defaults.headers.common['X-Requested-With'];
				//$http.defaults.headers.common['Content-Type'] = 'application/json';
				// Make an AJAX call to check if the user is logged in
				$http.get($rootScope.rootUrl + '/sec/loggedin').success(function (user) {
					// Authenticated
					if (user == true)
						$timeout(deferred.resolve, 0);

					// Not Authenticated
					else {
						$rootScope.message = 'You need to log in.';
						$timeout(function () {
							deferred.reject();
						}, 0);
						$location.url('/login');
					}
				});
			};


			//================================================
			// Add an security interceptor
			//================================================
			$httpProvider.interceptors.push(function ($q, $location) {

				return {
					// optional method
					'request': function(config) {
						return config || $q.when(config);
					},
					'response': function(response) {
						if (response.status === 401 || response.status === 404)
							$location.url('/login').replace();
						return (response);
					}
				};
			});



			var numLoadings = 0;
			var loadingScreen = $('<div style="position:fixed;top:0;left:0;right:0;bottom:0;z-index:10000;background-color:gray;background-color:rgba(70,70,70,0.2);"><img style="position:absolute;top:50%;left:50%;" alt="" src="data:image/gif;base64,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" /></div>')
				.appendTo($('body')).hide();
			$httpProvider.responseInterceptors.push(function () {
				return function (promise) {
					numLoadings++;
					loadingScreen.show();
					var hide = function (r) {
						if (!(--numLoadings)) loadingScreen.hide();
						return r;
					};
					return promise.then(hide, hide);
				};
			});

			$urlRouterProvider.otherwise("/");


			$stateProvider
				.state('parent', {
					abstract: true,
					templateUrl: "views/main.html"
				})
				.state('parent.root', {
					url: "/",
					views: {
						"@": { templateUrl: "views/no-tree.html" },
						"header-view@": { templateUrl: "views/partials/default-header.html" },
						"menu-view@parent.root": { templateUrl: "views/partials/default-menu.html", controller: 'DefaultNavigationController' },
						"content-view@parent.root": { templateUrl: "views/startpage.html" }
					}
				})
			;

			var init = function () {
					angular.forEach(RTConfig.views, function (view) {
						var viewDef = {};

						if (view.state.template === undefined) {
							viewDef['@'] = { templateUrl: "views/no-tree.html" };
						} else {
							viewDef['@'] = { templateUrl: view.state.template };
						}

						if (view.state.parent !== undefined) {
							viewDef.parent = view.state.parent;
						}

						if (view.state.header !== undefined) {
							if (view.state.header.html !== undefined) {
								var headerTmpl = { templateUrl: view.state.header.html };
							}
							if (view.state.header.controller !== undefined) {
								var headerCtrl = { templateUrl: view.state.header.controller };
							}
							viewDef[view.state.header.name] = route.resolve(view.name, headerTmpl, headerCtrl);
						}

						if (view.state.menu !== undefined) {
							if (view.state.menu.html !== undefined) {
								var menuTmpl = { templateUrl: view.state.menu.html };
							}
							if (view.state.menu.controller !== undefined) {
								var menuCtrl = { templateUrl: view.state.menu.controller };
							}
							viewDef[view.state.menu.name] = route.resolve(view.name, menuTmpl, menuCtrl);
						}

						if (view.state.content !== undefined) {
							if (view.state.content.html !== undefined) {
								var contentTmpl = { templateUrl: view.state.content.html };
							}
							if (view.state.content.controller !== undefined) {
								var contentCtrl = { templateUrl: view.state.content.controller };
							}
							viewDef[view.state.content.name] = route.resolve(view.name, contentTmpl, contentCtrl);
						}

						if (view.state.details !== undefined) {
							if (view.state.details.html !== undefined) {
								var detailsTmpl = { templateUrl: view.state.details.html };
							}
							if (view.state.details.controller !== undefined) {
								var detailsCtrl = { templateUrl: view.state.details.controller };
							}
							viewDef[view.state.details.name] = route.resolve(view.name, detailsTmpl, detailsCtrl);
						}

						$stateProvider.state(view.state.name, {
							url: view.url,
							views: viewDef
						});
					});
			}();

			// Do this after routes where loaded dynamically so that security urls cannot be overridden
			var loadSecurity = function () {
					angular.forEach(SecurityConfig.views, function (view) {
						var viewDef = {};
						viewDef['@'] = { templateUrl: "views/no-tree.html" };
						viewDef[view.state.header.name] = { templateUrl: "views/partials/default-header.html" };
						//viewDef[view.state.menu.name] = { templateUrl: "views/partials/default-menu.html", controller: 'DefaultNavigationController' };
						viewDef[view.state.content.name] = route.resolve(view.name);

						$stateProvider.state(view.state.name, {
							url: view.url,
							views: viewDef
						});
					});
			}();
			/*
			$stateProvider
				.state('parent.login', {
					url: "/login",
					views: {
						"@": { templateUrl: "views/no-tree.html" },
						"header-view@": { templateUrl: "views/partials/default-header.html" },
						"content-view@parent.login": { templateUrl: "login.html", controller: LoginCtrl }
					}
				})
				.state('parent.logout', {
					url: "/logout",
					views: {
						"@": { templateUrl: "views/no-tree.html" },
						"header-view@": { templateUrl: "views/partials/default-header.html" },
						"content-view@parent.logout": { templateUrl: "login.html", controller: LoginCtrl }
					}
				})
			;
			 */
		}]);

	app.run(function ($rootScope, $state, $stateParams, $http, $location, $window, $translate, editableOptions, localStorageService, CoreConfig, DialogService) {

		// xeditable
		editableOptions.theme = 'bs3';

		//$rootScope.env = envModel.env;
		$rootScope.DEVMODE = CoreConfig.env.DEVMODE;
		//$rootScope.rootUrl = "http://backend.openwms.cloudbees.net";
		$rootScope.rootUrl = CoreConfig.env.backendUrl; //"http://localhost:8080/org.openwms.client.rest.provider";

		/* Security */
		$rootScope.message = '';
		$rootScope.modal = {opened: false};

		$rootScope.global = {message: {short: ""}};

		// Logout function is available in any pages
		$rootScope.logout = function () {
			localStorageService.remove(CoreConfig.const.AUTH_TOKEN);
			$location.url('/logout').replace();
//			$http.post($rootScope.rootUrl+'/logout');
		};

		$rootScope.isLoggedIn = function () {
			return localStorageService.get(CoreConfig.const.AUTH_TOKEN) !== undefined;
		};
		$rootScope.getToken = function () {
			return localStorageService.get(CoreConfig.const.AUTH_TOKEN);
		};
		$rootScope.getTenantId = function () {
			return localStorageService.get(CoreConfig.const.TENANT_ID);
		};

		$rootScope.getHeader = function() {
			var h = {
        'Auth-Token': $rootScope.getToken(),
        'Tenant': $rootScope.getTenantId(),
        'Accept-Language':$translate.use().replace('_', '-')
      };
      return h;
		};

		/* State changing */
		$rootScope.$state = $state;
		$rootScope.$stateParams = $stateParams;


		$rootScope.$on(CoreConfig.events.INVALID_CREDENTIALS, function (event, next, current) {
			localStorageService.remove(CoreConfig.const.AUTH_TOKEN);
			localStorageService.remove(CoreConfig.const.TENANT_ID);
		});
		$rootScope.$on(CoreConfig.events.APP_LOGOUT, function (event, next, current) {
			localStorageService.remove(CoreConfig.const.AUTH_TOKEN);
			localStorageService.remove(CoreConfig.const.TENANT_ID);
			$location.url('/logout').replace("", "");
		});

		$rootScope.$on(CoreConfig.events.RETRIEVED_TOKEN, function (event, next, current) {
			// when we are coming from the login page and succeeded to login we go forward to the home screen
			localStorageService.set(CoreConfig.const.AUTH_TOKEN, next.token);
			localStorageService.set(CoreConfig.const.TENANT_ID, next.tenantId);
			$rootScope.$emit(CoreConfig.events.SUCCESSFULLY_LOGGED_IN, next);
			if ($rootScope.targetUrl === undefined || ($rootScope.targetUrl === '/account' && next !== undefined)) {
				$location.url('/home').replace("", "");
			} else {
				$location.url($rootScope.targetUrl).replace("", "");
			}
		});
		$rootScope.$on('$stateChangeStart', function (event, toState, toParams, fromState, fromParams) {
			// if route requires auth and user is not logged in
			// First check if a modal dialog is change and reject route change
			if (DialogService.modalOpened) {
				event.preventDefault();
			}
			if (!($location.url() !== '/account') && !$rootScope.isLoggedIn()) {
				// redirect back to login
				$rootScope.targetUrl = $location.url();
				$location.url('/account').replace("", "");
			}
		});
		$rootScope.$on('$routeChangeStart', function (event, next, current) {
			// if route requires auth and user is not logged in
			if (!routeClean($location.url()) && !$rootScope.isLoggedIn()) {
				$rootScope.targetUrl = $location.url();
				// redirect back to login
				$location.url('/account').replace("", "");
			}
		});
		$rootScope.$on('$viewContentLoaded', function () {
			if ($rootScope.DEVMODE) {
				console.log("-------------------------------------------");
				console.log("--       DEVELOPMENT MODE ENABLED        --");
				console.log("--       RUNNING AS   SYSTEM USER        --");
				console.log("-------------------------------------------");
				$rootScope.user = {};
				$rootScope.user.username = 'openwms';
				$rootScope.user.password = 'openwms';
				$rootScope.$broadcast(CoreConfig.events.APP_LOGIN);
				//$rootScope.login();
				//$location.url('/account').replace("", "");
				return;
			}
/*
			 if ($rootScope.modal.opened == false) {
			 $rootScope.modal.opened = true
			 $('#loginDialog').modal('show');
			 }
*/
		});
		$rootScope.$on('$translatePartialLoaderStructureChanged', function () {
			$translate.refresh();
		});

	});

	app.factory('rootApply', [ '$rootScope', function ($rootScope) {
		return function (fn, scope) {
			var args = [].slice.call(arguments, 1);

			// push null as scope if necessary
			args.length || args.push(null);

			return function () {
				// binds to the scope and any arguments
				var callFn = fn.bind.apply(
					fn
					, args.slice().concat([].slice.call(arguments))
				);

				// prevent applying/digesting twice
				$rootScope.$$phase
					? callFn()
					: $rootScope.$apply(callFn)
				;
			}
		};
	}]);

	return app;
});

