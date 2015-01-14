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

define(['angular'], function () {

  var routeResolver = function () {

    this.$get = function () {
      return this;
    };

    this.routeConfig = function () {
      var viewsDirectory = 'views/',
        controllersDirectory = 'scripts/controllers/',

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

      var resolve = function (baseName, tmp, ctrl) {
          var path = '';

          var routeDef = {};
          if (tmp === undefined) {
            routeDef.templateUrl = routeConfig.getViewsDirectory() + path + baseName + '.html';
          } else {
            routeDef.templateUrl = path + tmp.templateUrl;
          }
          if (ctrl === undefined) {
            routeDef.controller = baseName + 'Controller';
          } else if (ctrl === "") {
            // dont set controller
          } else {
            routeDef.controller = ctrl.templateUrl;
          }
          routeDef.resolve = {
            load: ['$q', '$rootScope', function ($q, $rootScope) {
              var dependencies = [routeConfig.getControllersDirectory() + path + routeDef.controller + '.js'];
              return resolveDependencies($q, $rootScope, dependencies);
            }]
          };

          return routeDef;
        },

        resolveDependencies = function ($q, $rootScope, dependencies) {
          var defer = $q.defer();
          require(dependencies, function () {
            defer.resolve();
            $rootScope.$apply();
          });

          return defer.promise;
        };

      return {
        resolve: resolve
      };
    }(this.routeConfig);

  };

  var servicesApp = angular.module('routeResolverServices', []);

  //Must be a provider since it will be injected into module.config()
  servicesApp.provider('routeResolver', routeResolver);
});
