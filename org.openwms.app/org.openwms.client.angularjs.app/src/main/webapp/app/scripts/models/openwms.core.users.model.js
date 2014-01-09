/*
 * openwms.org, the Open Warehouse Management System.
 *
 * This file is part of openwms.org.
 *
 * openwms.org is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * openwms.org is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software. If not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */

/*
 * openwms.core.users.model.js
 * Model module
 */

/*jslint         browser : true, continue : true,
 devel  : true, indent  : 2,    maxerr   : 50,
 newcap : true, nomen   : true, plusplus : true,
 regexp : true, sloppy  : true, vars     : false,
 white  : true
 */

/*global $, openwms */
angular.module('openwms.core.users.model', function () {
	'use strict';

	var
		configMap = { anon_id : 'a0' },
		stateMap  = {
			anon_user      : null,
			people_cid_map : {},
			people_db      : TAFFY()
		},
		isFakeData = true,
		personProto, makePerson, people, initModule;

	var userProto = {
		isPersistent : function() {
			return this.id != null;
		}
	};

	var set_users_list = function ( users ) {
		var user_map = users[ 0 ];
		delete stateMap.people_cid_map[ user_map.cid ];
		stateMap.user.cid     = user_map._id;
		stateMap.user.id      = user_map._id;
		stateMap.user.css_map = user_map.css_map;
		stateMap.people_cid_map[ user_map._id ] = stateMap.user;

		// When we add chat, we should join here
		$.gevent.publish( 'spa-login', [ stateMap.user ] );
	};
	return {
		set_users_list : set_users_list
	};
});