import { Injectable } from '@angular/core';
import { HttpEvent, HttpInterceptor, HttpHandler, HttpRequest } from '@angular/common/http';
import { Observable } from 'rxjs/internal/Observable';

@Injectable()
export class XhrInterceptor implements HttpInterceptor {
  // For backend, if requestedWith is null，then it's sync request; if requestedWith is XMLHttpRequest, then it's Ajax request.
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    const xhr = request.clone({
      // headers: request.headers.set("X-Requested-With", "XMLHttpRequest")
      setHeaders: { 'X-Requested-With': 'XMLHttpRequest', 'Content-Type': 'application/json' }
    });
    return next.handle(xhr);
  }
}
