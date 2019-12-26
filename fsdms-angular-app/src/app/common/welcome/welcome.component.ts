import { Component, OnInit } from '@angular/core';
import { SecurityService } from '../../service/security.service';
import { LogService } from '../../service/log.service';

@Component({
  selector: 'app-welcome',
  templateUrl: './welcome.component.html',
  styleUrls: ['./welcome.component.less']
})
export class WelcomeComponent implements OnInit {

  respMsg: string;
  currentUser: string;
  timeShow1: number;
  timeShow2: number;

  constructor(private securitySrv: SecurityService, private logSrv: LogService) { }

  ngOnInit() {
    this.logSrv.log('WelcomeComponent-ngOnInit');
  }

  onTest() {
    this.timeShow1 = (new Date('2019-12-02 00:54:00')).getTime();
    this.timeShow2 = Date.now();

    // for test only
    if (this.securitySrv.hasRole('admin')) {
      this.securitySrv.authAdmin().subscribe(
        response => {
          this.respMsg = JSON.stringify(response);
          this.currentUser = JSON.stringify(this.securitySrv.getCurrentUser());
        }
      );
    } else {
      this.securitySrv.authenticated().subscribe(
        response => {
          this.respMsg = JSON.stringify(response);
          this.currentUser = JSON.stringify(this.securitySrv.getCurrentUser());
        }
      );
    }

  }
}
