System.register(['angular2/core', './user-service'], function(exports_1, context_1) {
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
    var core_1, user_service_1;
    var UsersTableComponent;
    return {
        setters:[
            function (core_1_1) {
                core_1 = core_1_1;
            },
            function (user_service_1_1) {
                user_service_1 = user_service_1_1;
            }],
        execute: function() {
            UsersTableComponent = (function () {
                function UsersTableComponent(_userService) {
                    this._userService = _userService;
                }
                // populate model
                UsersTableComponent.prototype.ngOnInit = function () {
                    this._userService.users = this.users;
                };
                UsersTableComponent.prototype.click = function (answer) {
                    this._userService.users = this.users;
                };
                __decorate([
                    core_1.Input(), 
                    __metadata('design:type', Array)
                ], UsersTableComponent.prototype, "users", void 0);
                UsersTableComponent = __decorate([
                    core_1.Component({
                        selector: 'core-users-table',
                        templateUrl: 'app/uaa/users-table.component.html'
                    }), 
                    __metadata('design:paramtypes', [user_service_1.UserService])
                ], UsersTableComponent);
                return UsersTableComponent;
            }());
            exports_1("UsersTableComponent", UsersTableComponent);
        }
    }
});
//# sourceMappingURL=users-table.component.js.map