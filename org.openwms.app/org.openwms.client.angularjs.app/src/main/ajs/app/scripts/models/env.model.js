'use strict';

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

    var coreEnvModel = angular.module('coreEnvModel', []);

    var config = {

        env: {
            "DEVMODE": false,
            "backendUrl": 'http://localhost:8080/org.openwms.client.rest.provider',
//			"backendUrl" : 'http://stampback.cfapps.io',
            "buildNumber": '${build.number}',
            "buildDate": '${build.date}'
        },

        const: {
            AUTH_TOKEN: 'Auth-Token',
            TENANT_ID: 'Tenant',
            USER_PROFILE: 'UserProfile',
            USER_LANG: 'Language'
        },

        url: {
            security: {
                login: '/security/login',
                loggedin: '/sec/loggedin'
            }
        },

        events: {
            APP_LOGIN: 'CORE_APP_LOGIN',
            APP_LOGOUT: 'CORE_APP_LOGOUT',
            RETRIEVED_TOKEN: 'CORE_RETRIEVED_TOKEN',
            SUCCESSFULLY_LOGGED_IN: 'SUCCESSFULLY_LOGGED_IN',
            INVALID_CREDENTIALS: 'CORE_INVALID_CREDENTIALS',
            CLEAR_LOCAL_CACHE: 'CLEAR_LOCAL_CACHE'
        }
    };

    coreEnvModel.constant('CoreConfig', config);
});
