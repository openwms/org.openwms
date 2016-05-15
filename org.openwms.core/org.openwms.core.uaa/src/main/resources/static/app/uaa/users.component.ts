import { Component, OnInit } from 'angular2/core';
import { Router } from 'angular2/router';

@Component({
    selector: 'view-users',
    templateUrl: 'app/uaa/users.component.html',
    styleUrls: ['app/uaa/css/users.component.css']
})
export class UsersComponent implements OnInit {

    constructor(
        private _router: Router
    ){}

    ngOnInit() {
    }
}
