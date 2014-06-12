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
 */

define(['app'], function (app) {

	var srv = function ($q, $modal) {

		var result = {};

		var modalDefaults = {
			backdrop: true,
			keyboard: true,
			modalFade: true,
			templateUrl: '/views/partials/dialog.html'
		};

		var modalOptions = {
			nextButtonText: '<<Next>>',

			closeButtonText: '<<Close>>',
			showCloseButton: true,

			backButtonText: '<<Back>>',
			showBackButton: false,

			actionButtonText: '<<OK>>',
			actionDisabled: false,

			headerText: '<<Proceed?>>',
			bodyText: '<<Perform this action?>>',
			closeAfterAction: true,
			action: {},
			data: {
			}
		};

		/* Indicated that a modal dialog is currently opened. */
		result.modalOpened = false;

		result.showModal = function (customModalDefaults, customModalOptions) {
			if (!customModalDefaults) customModalDefaults = {};
			customModalDefaults.backdrop = 'static';
			return result.show(customModalDefaults, customModalOptions);
		};

		result.show = function (customModalDefaults, customModalOptions) {
			//Create temp objects to work with since we're in a singleton service
			var tempModalDefaults = {};
			var tempModalOptions = {};

			//Map angular-ui modal custom defaults to modal defaults defined in service
			angular.extend(tempModalDefaults, modalDefaults, customModalDefaults);

			//Map modal.html $scope custom properties to defaults defined in service
			angular.extend(tempModalOptions, modalOptions, customModalOptions);

			if (!tempModalDefaults.controller) {
				tempModalDefaults.controller = ['$scope','$modalInstance', 'DialogService',function ($scope, $modalInstance, DialogService) {
					$scope.modalOptions = tempModalOptions;
					$scope.modalOptions.callback = function (result) {
						//this.modalOpened = false;
						tempModalOptions.data.action = 'action';
						if(tempModalOptions.closeAfterAction) {
							$modalInstance.close(tempModalOptions.data);
							DialogService.modalOpened = false;
						}
					};
					$scope.modalOptions.close = function (result) {
						//this.modalOpened = false;
						tempModalOptions.data.action = 'cancel';
						$modalInstance.close(tempModalOptions.data);
						DialogService.modalOpened = false;
					};
				}];
			} else {


			}
			result.modalOpened = true;
			return $modal.open(tempModalDefaults).result;
		};


		return result;
	};

	app.factory('DialogService', ['$q', '$modal', srv]);
});