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
	'directives/UsersDirectives'
], function(angular, app, radio) {

	'use strict';

	radio('core_mod').subscribe(function(evt, data) {
		if (evt === 'LOAD_DIRECTIVES') {

			// Force loading all directives
			radio('core_mod').broadcast('LOAD_ALL_DIRECTIVES', data.module);
		}
		if (evt === 'ALL_DIRECTIVES_LOADED') {

			// The last directive definition publishes this event and forces a DIRECTIVES_LOADED.
			radio('core_mod').broadcast('DIRECTIVES_LOADED');
		}
	});
});