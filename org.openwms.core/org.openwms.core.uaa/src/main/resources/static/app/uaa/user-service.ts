import { Injectable } from 'angular2/core';
import { Http, Response, Headers, RequestOptions } from 'angular2/http';
import { Observable } from 'rxjs/Observable';
import { User } from './user.ts';
import { ApiGateway } from "./apiGateway.service";

@Injectable()
export class UserService {

    currentUser:User;
    users:User[];

    constructor(private _http:Http,
                private _apiGateway:ApiGateway) {
    }

    private _usersURL = 'http://localhost:8080/api/users';

    getUsers() {
        var stream = this._apiGateway.get(
            this._usersURL
            )
            .map(res => res.json().data._embedded.users);
        return stream;
    }
}
