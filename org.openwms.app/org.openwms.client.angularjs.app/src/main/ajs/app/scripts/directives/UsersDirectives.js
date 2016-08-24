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
 * A UsersDirectives provides directives for the 'User Management' screen.
 *
 * @module openwms.module.core
 * @author <a href="mailto:scherrer@openwms.org">Heiko Scherrer</a>
 * @version $Revision: $
 * @since 0.1
 */
define([
  'angular',
  'app',
  'radio'
], function (angular, app, radio) {

  'use strict';

  var chkUsers = function () {
    return {
      restrict: 'A',
      link: function (scope, element, attrs, ngModel) {

        element.on('blur keyup change', function () {
          if (element.val().length > 0) {
            scope.$apply(scope.selectedUsers = []);
            scope.$apply(read);
          } else if (element.val().length === 0) {
            scope.$apply(scope.selectedUsers = []);
          }
        });
        read(scope); // initialize

        // Write data to the model
        function read(scope) {
          angular.forEach(scope.userEntities, function (user) {
            if (user.username.toUpperCase().indexOf(element.val().toUpperCase()) !== -1 ||
              user.fullname.toUpperCase().indexOf(element.val().toUpperCase()) !== -1 ||
              (user.userDetails !== undefined && (user.userDetails.office.toUpperCase().indexOf(element.val().toUpperCase()) !== -1 ||
              user.userDetails.department.toUpperCase().indexOf(element.val().toUpperCase()) !== -1))) {
              scope.selectedUsers.push(user);
            }
          });
        }
      }
    };
  };

  radio('core_mod').subscribe(function (evt, module) {
    if (evt === 'LOAD_ALL_DIRECTIVES') {
      angular.module('app', ['openwms.core.module']).directive('chkUsers', ['$http', '$q', chkUsers]);

      radio('core_mod').broadcast('ALL_DIRECTIVES_LOADED', module);
    }
  });
});




