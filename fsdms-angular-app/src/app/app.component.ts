import { Component, OnInit } from '@angular/core';
import { LogService } from './service/log.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.less']
})
export class AppComponent implements OnInit {

  title = 'FSDMS';

  constructor(private logSrv: LogService) {
  }

  ngOnInit(): void {
    this.logSrv.log('AppComponent - ngOnInit');
    // signout auto after 30 mins
    // setTimeout(()=>{
    //   this.securitySrv.signOut();
    // }, 60*60*1000)
  }


}
