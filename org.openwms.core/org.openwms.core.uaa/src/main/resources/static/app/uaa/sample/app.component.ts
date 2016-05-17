import { Component, OnInit } from 'angular2/core';
import { Router, RouteConfig, ROUTER_DIRECTIVES, ROUTER_PROVIDERS } from 'angular2/router';
import { HTTP_PROVIDERS, Http, Response } from 'angular2/http';

import { User } from '../user';
import { UserService } from '../user-service';
import { UsersTableComponent } from '../users-table.component';

@Component({
    selector: 'core-users-app',
    templateUrl: 'app/uaa/sample/app.component.html',
    directives: [ROUTER_DIRECTIVES],
    providers: [
        ROUTER_PROVIDERS,
        HTTP_PROVIDERS,
        UserService
    ]
})
@RouteConfig([
    {
        path: '/',
        name: 'Root',
        component: UsersTableComponent,
        useAsDefault: true
    }
])
export class AppComponent implements OnInit {

    users: User[];

    constructor(
        private _router: Router,
        private _userService: UserService
    ) { }

    ngOnInit() {
        this._userService.getUsers()
            .subscribe(
                users => this.users = <any>users);
    }
}