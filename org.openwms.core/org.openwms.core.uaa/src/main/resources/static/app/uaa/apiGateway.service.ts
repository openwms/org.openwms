import { Injectable } from "angular2/core";  
import { Http, Headers, RequestMethod, RequestOptions, Response, URLSearchParams } from 'angular2/http'

import { Observable } from "rxjs/Observable";  
import { Subject } from "rxjs/Subject";
import 'rxjs/Rx';

export class ApiGatewayOptions {  
    method: RequestMethod;
    url: string;
    headers: Headers;
    params = {};
    data = {};
}

@Injectable()
export class ApiGateway {

    private errorsSubject = new Subject<any>();
    errors$: Observable<any>;

    private pendingCommandsSubject = new Subject<number>();
    private pendingCommandCount = 0;

    pendingCommands$: Observable<number>;

    constructor(
        private http: Http
    ) {
        this.errors$ = this.errorsSubject.asObservable();
        this.pendingCommands$ = this.pendingCommandsSubject.asObservable();

    }

    get(url: string, params?: any): Observable<Response> {
        let options = new ApiGatewayOptions();
        options.method = RequestMethod.Get;
        options.url = url;
        options.params = params;
        return this.request(options);
    }

    post(url: string, params: any, data: any): Observable<Response> {
        if (!data) {
            data = params;
            params = {};
        }
        let options = new ApiGatewayOptions();
        options.method = RequestMethod.Post;
        options.url = url;
        options.params = params;
        options.data = data;

        return this.request(options);
    }


    private request(options: ApiGatewayOptions): Observable<any> {

        options.method = (options.method || RequestMethod.Get);
        options.url = (options.url || "");
        options.headers = (options.headers || new Headers());
        options.params = (options.params || {});
        options.data = (options.data || {});

        this.addXsrfToken(options);
        this.addContentType(options);

        let requestOptions = new RequestOptions({
            method:options.method,
            headers:options.headers,
            body:JSON.stringify(options.data),
            url:options.url,
            search:this.buildUrlSearchParams(options.params)
        });

        let isCommand = (options.method !== RequestMethod.Get);
        if (isCommand) {
            this.pendingCommandsSubject.next(++this.pendingCommandCount);
        }

        let stream = this.http.request(options.url, requestOptions)
            .catch((error: any) => {
                this.errorsSubject.next(error);
                return Observable.throw(error);
            })
            .map(this.unwrapHttpValue)
            .catch((error: any) => {
                return Observable.throw(this.unwrapHttpError(error));
            })
            .finally(() => {
                if (isCommand) {
                    this.pendingCommandsSubject.next(--this.pendingCommandCount);
                }
            });

        return stream;
    }


    private addContentType(options: ApiGatewayOptions): ApiGatewayOptions {
        if (options.method !== RequestMethod.Get) {
            options.headers["Content-Type"] = "application/json; charset=UTF-8";
        }
        return options;
    }

    private extractValue(collection: any, key: string): any {
        var value = collection[key];
        delete (collection[key]);
        return value;
    }

    private addXsrfToken(options: ApiGatewayOptions): ApiGatewayOptions {
        var xsrfToken = this.getXsrfCookie();
        if (xsrfToken) {
            options.headers["X-XSRF-TOKEN"] = xsrfToken;
        }
        return options;
    }

    private getXsrfCookie(): string {
        var matches = document.cookie.match(/\bXSRF-TOKEN=([^\s;]+)/);
        try {
            return (matches && decodeURIComponent(matches[1]));
        } catch (decodeError) {
            return ("");
        }
    }

    private buildUrlSearchParams(params: any): URLSearchParams {
        var searchParams = new URLSearchParams();
        for (var key in params) {
            searchParams.append(key, params[key])
        }
        return searchParams;
    }

    private unwrapHttpError(error: any): any {
        try {
            return (error.json());
        } catch (jsonError) {
            return ({
                code: -1,
                message: "An unexpected error occurred."
            });
        }
    }

    private unwrapHttpValue(value: Response): any {
        return (value.json());
    }
}