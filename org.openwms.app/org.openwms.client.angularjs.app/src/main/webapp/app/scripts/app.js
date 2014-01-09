'use strict';

//var openwms_app = angular.module('openwms_app');
var openwms_root = angular.module('openwms_root', ['ui.bootstrap', 'ui.router', 'openwms_app', 'ngResource', 'toaster']);

openwms_root.factory('rootApply', [ '$rootScope', function ($rootScope) {
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
} ]);

openwms_root
	.config(function ($stateProvider, $urlRouterProvider, $locationProvider, $httpProvider, $compileProvider) {

		$compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|ftp|mailto|file|data):/);

		delete $httpProvider.defaults.headers.common['X-Requested-With'];
		$httpProvider.defaults.headers.common['Content-Type'] = 'application/json';

		var checkLoggedin = function ($q, $timeout, $http, $location, $rootScope) {
			// Initialize a new promise
			var deferred = $q.defer();
			delete $http.defaults.headers.common['X-Requested-With'];
			$http.defaults.headers.common['Content-Type'] = 'application/json';
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
		}

		//================================================
		// Add an interceptor for AJAX errors
		//================================================
		$httpProvider.responseInterceptors.push(function ($q, $location) {
			return function (promise) {
				return promise.then(
					// Success: just return the response
					function (response) {
						return response;
					},
					// Error: check the error status to get only the 401
					function (response) {
						if (response.status == 401)
							$location.url('/login');
						return $q.reject(response);
					}
				);
			}
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
					"menu-view@parent.root": { templateUrl: "views/partials/default-menu.html", controller: 'NavigationCtrl' },
					"content-view@parent.root": { templateUrl: "views/startpage.html" }
				}
			})
			.state('parent.users', {
				url: "/users",
				views: {
					"@": { templateUrl: "views/no-tree.html" },
					"header-view@": { templateUrl: "views/partials/default-header.html" },
					"menu-view@parent.users": { templateUrl: "views/partials/default-menu.html", controller: 'NavigationCtrl' },
					"content-view@parent.users": { templateUrl: "views/users.html", controller: 'UsersCtrl' }
				}
			})
			.state('parent.roles', {
				url: "/roles",
				views: {
					"@": { templateUrl: "views/no-tree.html" },
					"header-view@": { templateUrl: "views/partials/default-header.html" },
					"menu-view@parent.roles": { templateUrl: "views/partials/default-menu.html", controller: 'NavigationCtrl' },
					"content-view@parent.roles": { templateUrl: "views/roles.html", controller: 'RolesCtrl' }
				}
			})
			.state('parent.login', {
				url: "/login",
				views: {
					"@": { templateUrl: "views/no-tree.html" },
					"header-view@": { templateUrl: "views/partials/default-header.html" },
					"content-view@parent.login": { templateUrl: "login.html", controller: 'LoginCtrl' }
				}
			})
			.state('parent.logout', {
				url: "/logout",
				views: {
					"@": { templateUrl: "views/no-tree.html" },
					"header-view@": { templateUrl: "views/partials/default-header.html" },
					"content-view@parent.logout": { templateUrl: "login.html", controller: 'LoginCtrl' }
				}
			})
		;
		/*
		 $routeProvider
			.when('/', {
				templateUrl: 'views/main.html',
				controller: 'openwms_app.MainCtrl'
			})
			/*
			 .when('/users', {
			 templateUrl: 'views/users.html',
			 controller: 'UsersCtrl'
			 //			resolve: {
			 //				loggedin: checkLoggedin
			 //			}
			 })
			 .when('/roles', {
			 templateUrl: 'views/roles.html',
			 controller: 'RolesCtrl'
			 //			resolve: {
			 //				loggedin: checkLoggedin
			 //			}
			 })
			 .when('/login', {
			 templateUrl: 'login.html',
			 controller: 'LoginCtrl'
			 })

		 .otherwise({
				redirectTo: '/'
			});
		 */
	}).run(function ($rootScope, $state, $stateParams, $http, $location) {
		$rootScope.DEVMODE = true;
		$rootScope.rootUrl = 'http://localhost:8080/org.openwms.client.rest.provider';

		/* Security */
		$rootScope.message = '';
		$rootScope.modal = {opened: false};

		$rootScope.global = {message: {short:""}};

		/* Toaster
		toaster.options = {
			"closeButton": false,
			"debug": false,
			"positionClass": "toast-top-full-width",
			"onclick": null,
			"showDuration": "000",
			"hideDuration": "5000",
			"timeOut": "500",
			"extendedTimeOut": "5000",
			"showEasing": "swing",
			"hideEasing": "linear",
			"showMethod": "fadeIn",
			"hideMethod": "fadeOut"
		}
		 */
		// Logout function is available in any pages
		$rootScope.logout = function () {
			$rootScope.authToken = null;
			$location.url('/logout').replace();
//			$http.post($rootScope.rootUrl+'/logout');
		};

		$rootScope.isLoggedIn = function () {
			return $rootScope.authToken != null;
		};

		/* State changing */
		$rootScope.$state = $state;
		$rootScope.$stateParams = $stateParams;
		// enumerate routes that don't need authentication
		var routesThatDontRequireAuth = ['/login'];
		// check if current location matches route
		var routeClean = function (route) {
			return _.find(routesThatDontRequireAuth,
				function (noAuthRoute) {
					return _.str.startsWith(route, noAuthRoute);
				});
		};
		$rootScope.$on('$stateChangeStart', function (event, next, current) {
			// if route requires auth and user is not logged in
			if (!routeClean($location.url()) && !$rootScope.isLoggedIn()) {
				// redirect back to login
				$location.url('/login').replace();
			}
		});
		$rootScope.$on('$routeChangeStart', function (event, next, current) {
			// if route requires auth and user is not logged in
			if (!routeClean($location.url()) && !$rootScope.isLoggedIn()) {
				// redirect back to login
				$location.url('/login').replace();
			}
		});
	});


/**********************************************************************
 * Login controller
 **********************************************************************/
openwms_root.controller('LoginCtrl', function ($scope, $rootScope, $http, $location) {
	// This object will be filled by the form
	$scope.user = {};

	// Register the login() function
	$scope.login = function () {
		$('#loginDialog').modal('hide');
		$scope.modal.opened = false;
		$http.defaults.headers.post['Auth-Token'] = $rootScope.authToken;
		$http.post($rootScope.rootUrl + '/sec/login', {
			username: $scope.user.username,
			password: $scope.user.password
		})
			.success(function (user) {
				$rootScope.user = user;
				$rootScope.message = undefined;
				$rootScope.authToken = user.token;
				$location.url('/');
				//$('#loginDialog').modal('hide');
				//$scope.modal.opened = false;
			})
			.error(function (data, status, headers, config) {
				$rootScope.message = 'Authentication failed.';
				$rootScope.authToken = null;
				$scope.modal.opened = true;
				$('#loginDialog').modal('show');
				//$location.url('/login').replace();
			});
	};

	$scope.$on('$viewContentLoaded', function () {
		if ($scope.DEVMODE) {
			console.log("-------------------------------------------");
			console.log("--       DEVELOPMENT MODE ENABLED        --");
			console.log("--       RUNNING WITH SYSTEM USER        --");
			console.log("-------------------------------------------");
			$scope.user.username = 'openwms';
			$scope.user.password = 'openwms';
			$scope.login();
			return;
		}
		if ($scope.modal.opened == false) {
			$scope.modal.opened = true
			$('#loginDialog').modal('show');
		}
		//$(".assign-list").dataTable();
	});
});
