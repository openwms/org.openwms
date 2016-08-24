import { Injectable, provide, APP_INITIALIZER } from 'angular2/core';
import { Router, RouteConfig, ROUTER_DIRECTIVES } from 'angular2/router';
import { bootstrap } from 'angular2/platform/browser';
import { HTTP_PROVIDERS, BaseRequestOptions, RequestOptions } from 'angular2/http'

import { AppComponent } from './app.component';
import { ApiGateway } from '../apiGateway.service';
import { HttpErrorHandler } from "./httpErrorHandler";
import 'rxjs/Rx';


bootstrap(AppComponent, [
  HTTP_PROVIDERS,
  ApiGateway,
  HttpErrorHandler,
  provide(APP_INITIALIZER, {
        useFactory: (httpErrorHandler) => {
            console.info( "HttpErrorHandler initialized." );
        },
        deps: [HttpErrorHandler]
    })
]);