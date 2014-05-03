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
define([
	'angular'/*,
	'app',
	'radio',
	'services/openwmsCoreServices',
	'controllers/openwmsCoreControllers',
	'directives/openwmsCoreDirectives'*/
], function(angular/*, app, radio*/) {

	'use strict';

	var module = angular.module('openwmsCoreModule', []);

	/*
	radio('appl').subscribe(function(evt, data) {
		if (evt === 'LOAD_MODULE') {
			if (data.module.name === 'CORE_MODULE') {
				radio('core_mod').broadcast('LOAD_SERVICES', {module: angular.module('openwmsCoreModule', [])});
			}
		}
	});

	radio('core_mod').subscribe(function(evt) {
		if (evt === 'SERVICES_LOADED') {
			radio('core_mod').broadcast('LOAD_CONTROLLERS', { module: angular.module('openwmsCoreModule', []) });
		}
		if (evt === 'CONTROLLERS_LOADED') {
			radio('core_mod').broadcast('LOAD_DIRECTIVES', angular.module('openwmsCoreModule', []));
		}
		if (evt === 'DIRECTIVES_LOADED') {
			radio('core_mod').broadcast('MODULE_LOADED', angular.module('openwmsCoreModule', []));
		}
	});
*/
	return module;
});