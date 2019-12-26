import { Injectable } from '@angular/core';
import {
  HttpEvent,
  HttpRequest,
  HttpResponse,
  HttpErrorResponse,
  HttpHandler,
  HttpInterceptor
} from '@angular/common/http';
import { Observable, of, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { catchError } from 'rxjs/operators';
import { LogService } from '../service/log.service';

@Injectable()
export class ResponseInterceptor implements HttpInterceptor {

  constructor(private router: Router, private logSrv: LogService) { }

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    return next
      .handle(req)
      .pipe(catchError((err: HttpErrorResponse) => this.handleData(err)));
  }

  private handleData(
    event: HttpResponse<any> | HttpErrorResponse
  ): Observable<any> {
    switch (event.status) {
      case 401:
        this.logSrv.log('ResponseInterceptor-Unauthorized: ', event);
        this.router.navigate(['signin']);
        return of(event);
        break;
      case 403:
        this.logSrv.log('ResponseInterceptor-Forbidden: ', event);
        this.router.navigate(['403']);
        return of(event);
        break;
      case 404:
        this.logSrv.log('ResponseInterceptor-PageNotFound: ', event);
        this.router.navigate(['PageNotFound']);
        return of(event);
        break;
      case 500:
        this.logSrv.log('ResponseInterceptor-InternalServerError: ', event);
        this.router.navigate(['500']);
        return of(event);
        break;
      default:
        this.logSrv.log('ResponseInterceptor-ERROR: ', event);
    }
    return throwError(event);
  }
}
