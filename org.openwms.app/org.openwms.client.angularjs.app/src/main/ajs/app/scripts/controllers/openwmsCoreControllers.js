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
define([
	'angular',
	'app',
	'radio',
	'services/openwmsCoreServices',
	'controllers/DefaultNavigationController',
	'controllers/UsersController'
], function(angular, app, radio) {

	'use strict';

	radio('core_mod').subscribe(function(evt, data) {
		if (evt === 'LOAD_CONTROLLERS') {

			// Force loading all controllers
			radio('core_mod').broadcast('LOAD_ALL_CONTROLLERS', data.module);
		}
		if (evt === 'ALL_CONTROLLERS_LOADED') {

			// The last controller publishes this event and forces a CONTROLLERS_LOADED.
			radio('core_mod').broadcast('CONTROLLERS_LOADED');
		}
	});


	//var app = angular.module('openwms.core.module', ['ui.bootstrap', 'ngAnimate', 'toaster', 'angularFileUpload', 'base64']);
	//app.controller('UsersController', ['$http', '$q', 'CoreService', sub.UsersController]);
	//app.controller('DefaultNavigationController', ['$scope', sub2.DefaultNavigationController]);
	//app.directive('chkUsers', sub.chkUsers);
});