System.register(['angular2/core', 'angular2/platform/browser', 'angular2/http', './app.component', '../apiGateway.service', "./httpErrorHandler", 'rxjs/Rx'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var core_1, browser_1, http_1, app_component_1, apiGateway_service_1, httpErrorHandler_1;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (browser_1_1) {
                browser_1 = browser_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (app_component_1_1) {
                app_component_1 = app_component_1_1;
            },
            function (apiGateway_service_1_1) {
                apiGateway_service_1 = apiGateway_service_1_1;
            },
            function (httpErrorHandler_1_1) {
                httpErrorHandler_1 = httpErrorHandler_1_1;
            },
            function (_1) {}],
        execute: function() {
            browser_1.bootstrap(app_component_1.AppComponent, [
                http_1.HTTP_PROVIDERS,
                apiGateway_service_1.ApiGateway,
                httpErrorHandler_1.HttpErrorHandler,
                core_1.provide(core_1.APP_INITIALIZER, {
                    useFactory: function (httpErrorHandler) {
                        console.info("HttpErrorHandler initialized.");
                    },
                    deps: [httpErrorHandler_1.HttpErrorHandler]
                })
            ]);
        }
    }
});
//# sourceMappingURL=main.js.map