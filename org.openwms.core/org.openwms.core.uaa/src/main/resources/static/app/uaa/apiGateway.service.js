System.register(["angular2/core", 'angular2/http', "rxjs/Observable", "rxjs/Subject", 'rxjs/Rx'], function(exports_1, context_1) {
    "use strict";
    var __moduleName = context_1 && context_1.id;
    var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
        var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
        if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
        else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
        return c > 3 && r && Object.defineProperty(target, key, r), r;
    };
    var __metadata = (this && this.__metadata) || function (k, v) {
        if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
    };
    var core_1, http_1, Observable_1, Subject_1;
    var ApiGatewayOptions, ApiGateway;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (Observable_1_1) {
                Observable_1 = Observable_1_1;
            },
            function (Subject_1_1) {
                Subject_1 = Subject_1_1;
            },
            function (_1) {}],
        execute: function() {
            ApiGatewayOptions = (function () {
                function ApiGatewayOptions() {
                    this.params = {};
                    this.data = {};
                }
                return ApiGatewayOptions;
            }());
            exports_1("ApiGatewayOptions", ApiGatewayOptions);
            ApiGateway = (function () {
                function ApiGateway(http) {
                    this.http = http;
                    this.errorsSubject = new Subject_1.Subject();
                    this.pendingCommandsSubject = new Subject_1.Subject();
                    this.pendingCommandCount = 0;
                    this.errors$ = this.errorsSubject.asObservable();
                    this.pendingCommands$ = this.pendingCommandsSubject.asObservable();
                }
                ApiGateway.prototype.get = function (url, params) {
                    var options = new ApiGatewayOptions();
                    options.method = http_1.RequestMethod.Get;
                    options.url = url;
                    options.params = params;
                    return this.request(options);
                };
                ApiGateway.prototype.post = function (url, params, data) {
                    if (!data) {
                        data = params;
                        params = {};
                    }
                    var options = new ApiGatewayOptions();
                    options.method = http_1.RequestMethod.Post;
                    options.url = url;
                    options.params = params;
                    options.data = data;
                    return this.request(options);
                };
                ApiGateway.prototype.request = function (options) {
                    var _this = this;
                    options.method = (options.method || http_1.RequestMethod.Get);
                    options.url = (options.url || "");
                    options.headers = (options.headers || new http_1.Headers());
                    options.params = (options.params || {});
                    options.data = (options.data || {});
                    this.addXsrfToken(options);
                    this.addContentType(options);
                    var requestOptions = new http_1.RequestOptions({
                        method: options.method,
                        headers: options.headers,
                        body: JSON.stringify(options.data),
                        url: options.url,
                        search: this.buildUrlSearchParams(options.params)
                    });
                    var isCommand = (options.method !== http_1.RequestMethod.Get);
                    if (isCommand) {
                        this.pendingCommandsSubject.next(++this.pendingCommandCount);
                    }
                    var stream = this.http.request(options.url, requestOptions)
                        .catch(function (error) {
                        _this.errorsSubject.next(error);
                        return Observable_1.Observable.throw(error);
                    })
                        .map(this.unwrapHttpValue)
                        .catch(function (error) {
                        return Observable_1.Observable.throw(_this.unwrapHttpError(error));
                    })
                        .finally(function () {
                        if (isCommand) {
                            _this.pendingCommandsSubject.next(--_this.pendingCommandCount);
                        }
                    });
                    return stream;
                };
                ApiGateway.prototype.addContentType = function (options) {
                    if (options.method !== http_1.RequestMethod.Get) {
                        options.headers["Content-Type"] = "application/json; charset=UTF-8";
                    }
                    return options;
                };
                ApiGateway.prototype.extractValue = function (collection, key) {
                    var value = collection[key];
                    delete (collection[key]);
                    return value;
                };
                ApiGateway.prototype.addXsrfToken = function (options) {
                    var xsrfToken = this.getXsrfCookie();
                    if (xsrfToken) {
                        options.headers["X-XSRF-TOKEN"] = xsrfToken;
                    }
                    return options;
                };
                ApiGateway.prototype.getXsrfCookie = function () {
                    var matches = document.cookie.match(/\bXSRF-TOKEN=([^\s;]+)/);
                    try {
                        return (matches && decodeURIComponent(matches[1]));
                    }
                    catch (decodeError) {
                        return ("");
                    }
                };
                ApiGateway.prototype.buildUrlSearchParams = function (params) {
                    var searchParams = new http_1.URLSearchParams();
                    for (var key in params) {
                        searchParams.append(key, params[key]);
                    }
                    return searchParams;
                };
                ApiGateway.prototype.unwrapHttpError = function (error) {
                    try {
                        return (error.json());
                    }
                    catch (jsonError) {
                        return ({
                            code: -1,
                            message: "An unexpected error occurred."
                        });
                    }
                };
                ApiGateway.prototype.unwrapHttpValue = function (value) {
                    return (value.json());
                };
                ApiGateway = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http])
                ], ApiGateway);
                return ApiGateway;
            }());
            exports_1("ApiGateway", ApiGateway);
        }
    }
});
//# sourceMappingURL=apiGateway.service.js.map