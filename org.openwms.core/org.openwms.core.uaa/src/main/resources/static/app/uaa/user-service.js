System.register(['angular2/core', 'angular2/http', "./apiGateway.service"], function(exports_1, context_1) {
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
    var core_1, http_1, apiGateway_service_1;
    var UserService;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (apiGateway_service_1_1) {
                apiGateway_service_1 = apiGateway_service_1_1;
            }],
        execute: function() {
            UserService = (function () {
                function UserService(_http, _apiGateway) {
                    this._http = _http;
                    this._apiGateway = _apiGateway;
                    this._usersURL = 'http://localhost:8080/api/users';
                }
                UserService.prototype.getUsers = function () {
                    var stream = this._apiGateway.get(this._usersURL)
                        .map(function (res) { return res.json().data._embedded.users; });
                    return stream;
                };
                UserService = __decorate([
                    core_1.Injectable(), 
                    __metadata('design:paramtypes', [http_1.Http, apiGateway_service_1.ApiGateway])
                ], UserService);
                return UserService;
            }());
            exports_1("UserService", UserService);
        }
    }
});
//# sourceMappingURL=user-service.js.map