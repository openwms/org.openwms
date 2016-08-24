System.register(['angular2/core', 'angular2/router', 'angular2/http', '../user-service', '../users-table.component'], function(exports_1, context_1) {
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
    var core_1, router_1, http_1, user_service_1, users_table_component_1;
    var AppComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (router_1_1) {
                router_1 = router_1_1;
            },
            function (http_1_1) {
                http_1 = http_1_1;
            },
            function (user_service_1_1) {
                user_service_1 = user_service_1_1;
            },
            function (users_table_component_1_1) {
                users_table_component_1 = users_table_component_1_1;
            }],
        execute: function() {
            AppComponent = (function () {
                function AppComponent(_router, _userService) {
                    this._router = _router;
                    this._userService = _userService;
                }
                AppComponent.prototype.ngOnInit = function () {
                    var _this = this;
                    this._userService.getUsers()
                        .subscribe(function (users) { return _this.users = users; });
                };
                AppComponent = __decorate([
                    core_1.Component({
                        selector: 'core-users-app',
                        templateUrl: 'app/uaa/sample/app.component.html',
                        directives: [router_1.ROUTER_DIRECTIVES],
                        providers: [
                            router_1.ROUTER_PROVIDERS,
                            http_1.HTTP_PROVIDERS,
                            user_service_1.UserService
                        ]
                    }),
                    router_1.RouteConfig([
                        {
                            path: '/',
                            name: 'Root',
                            component: users_table_component_1.UsersTableComponent,
                            useAsDefault: true
                        }
                    ]), 
                    __metadata('design:paramtypes', [router_1.Router, user_service_1.UserService])
                ], AppComponent);
                return AppComponent;
            }());
            exports_1("AppComponent", AppComponent);
        }
    }
});
//# sourceMappingURL=app.component.js.map