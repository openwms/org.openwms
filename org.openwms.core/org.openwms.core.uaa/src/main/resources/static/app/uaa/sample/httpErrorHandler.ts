import { Injectable } from "angular2/core";
import { ApiGateway } from "../apiGateway.service";

@Injectable()
export class HttpErrorHandler {

    constructor(
        private _apiGateway: ApiGateway
    ) {
        _apiGateway.errors$.subscribe(
            (value: any) => {
                if (value.status === 401 || value.status === 403) {
                    window.location.reload();
                }
            });
    }
}