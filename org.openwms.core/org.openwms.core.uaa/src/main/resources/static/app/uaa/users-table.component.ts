import { Component, Input, OnInit } from 'angular2/core';
import { Router } from 'angular2/router';
import { User } from './user';
import { UserService } from './user-service';

@Component({
    selector: 'core-users-table',
    templateUrl: 'app/uaa/users-table.component.html'
})
export class UsersTableComponent implements OnInit {

    @Input() users: User[];

    constructor(
        private _router: Router,
        private _userService: UserService
    ) { }

    // populate model
    ngOnInit() {
        this._userService.users = this.users;
    }

    click(answer: User) {
        this._userService.users = this.users;
    }
}
