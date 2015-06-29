'use strict';
define(['app'], function (app) {

    var srv = function () {

        var result = {};

        result.resolveMultiple = function (data, delay) {
            if (data === undefined || data.items === undefined) {
                delay.reject(new Error("Not expected response"));
            } else {
                if (data.items[0].obj) {
                    delay.resolve(data.items[0].obj[0])
                }
                delay.resolve();
            }
        };

        result.resolveSingle = function (data, delay) {
            delay.resolve(data.items[0].obj[0]);
        };

        result.resolveIsCreated = function (data, delay) {
            if (data && data.httpStatus == '201') {
                delay.resolve();
            }
            delay.reject();
        };

        return result;
    };

    app.factory('ResponsedataResolver', [srv]);

});
