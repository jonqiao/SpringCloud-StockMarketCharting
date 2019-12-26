import { Injectable } from '@angular/core';
import { CanActivate, CanActivateChild, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { Router } from '@angular/router';
import { SecurityService } from '../service/security.service';
import { LogService } from '../service/log.service';
import { environment } from '../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class UserAuthGuard implements CanActivate, CanActivateChild {

  constructor(private router: Router, private securitySrv: SecurityService, private logSrv: LogService) { }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.logSrv.log('UserAuthGuard-canActivate called');
    if (environment.debug) {
      // For local test only
      return true;
    }

    if (!this.securitySrv.isLoggedIn()) {
      this.router.navigate(['signin']);
      return false;
    } else {
      return true;
    }

  }

  canActivateChild(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.logSrv.log('AdminAuthGuard-canActivateChild called');
    return this.canActivate(next, state);
  }

}
