import { Injectable } from '@angular/core';
import { HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs';
import { StorageService } from '../service/storage.service';
import { LogService } from '../service/log.service';

@Injectable()
export class TokenInterceptor implements HttpInterceptor {

  constructor(private storageSrv: StorageService, private logSrv: LogService) { }

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const token = this.storageSrv.getItem('token');
    this.logSrv.log('TokenInterceptor-token = ', token);

    if (token) {
      request = request.clone({
        // headers: request.headers.set('Authorization', "Bearer " + token)
        setHeaders: { Authorization: 'Bearer ' + token }
      });
    }

    return next.handle(request);
  }
}
