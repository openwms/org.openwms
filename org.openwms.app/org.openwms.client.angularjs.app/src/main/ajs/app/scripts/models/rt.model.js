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

/*
 * env.model.js
 * Environment model module
 */

/*jslint         browser : true, continue : true,
 devel  : true, indent  : 2,    maxerr   : 50,
 newcap : true, nomen   : true, plusplus : true,
 regexp : true, sloppy  : true, vars     : false,
 white  : true
 */

/*global $, openwms */
define(['angular'], function () {

	var module =
	{
		moduleName: "CORE",
		moduleVersion: "1.0.0",
		url: "core_module",
		views: [
			{
				name: "Roles",
				url: "/roles",
				state: {
					name: "parent.roles",
					header: {name: "header-view@parent.roles"},
					menu: {name: "menu-view@parent.roles"},
					content: {name: "content-view@parent.roles"}
				}
			},
			{
				name: "Users",
				url: "/users",
				state: {
					name: "parent.users",
					header: {name: "header-view@parent.users"},
					menu: {name: "menu-view@parent.users"},
					content: {name: "content-view@parent.users"}
				}
			}
		]
	};

	var coreRtModel = angular.module('rtModelModule', []);
	coreRtModel.constant('RTConfig', module);
})

