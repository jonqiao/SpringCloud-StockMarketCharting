import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { SecurityService } from '../../service/security.service';
import { LogService } from '../../service/log.service';
import { environment } from '../../../environments/environment';

@Component({
  selector: 'app-top-bar',
  templateUrl: './top-bar.component.html',
  styleUrls: ['./top-bar.component.less']
})
export class TopBarComponent implements OnInit {

  constructor(private router: Router, private route: ActivatedRoute,
              private securitySrv: SecurityService, private LogSrv: LogService) { }

  ngOnInit() { }

  chkRole(role: string): boolean {
    if (environment.debug) {
      // For local test only
      return true;
    }

    if (role === 'admin' && this.securitySrv.hasRole(role)) {
      return true;
    } else if (role === 'user' && this.securitySrv.hasRole(role)) {
      return true;
    } else {
      return false;
    }
  }

  isloggedIn() { return this.securitySrv.isLoggedIn(); }

  signout() {
    this.securitySrv.signOut();
    this.router.navigate(['signin']); // If no starting route is provided, the navigation is absolute.
    // this.router.navigateByUrl('/signin');  // must use absolute path
  }
}
