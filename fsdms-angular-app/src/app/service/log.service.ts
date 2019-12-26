import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  logSwitch = true; // disable/enable log-service

  constructor() { }

  getLogger() {
    // specify customized log-service, like save the logs into the disk
    return console;
  }

  info(message: any, ...optionalParams: any[]): void {
    if (this.logSwitch) {
      this.getLogger().info(message, ...optionalParams);
    }
  }

  log(message: any, ...optionalParams: any[]): void {
    if (this.logSwitch) {
      this.getLogger().log(message, ...optionalParams);
    }
  }

  error(message: any, ...optionalParams: any[]): void {
    if (this.logSwitch) {
      this.getLogger().error(message, ...optionalParams);
    }
  }

}
