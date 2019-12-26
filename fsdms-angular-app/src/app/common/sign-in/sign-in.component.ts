import { Component, OnInit } from '@angular/core';
import { FormBuilder, AbstractControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthenticationRequest } from '../../model/auth.request';
import { AuthenticationResponse } from '../../model/auth.response';
import { SecurityService } from '../../service/security.service';
import { StorageService } from '../../service/storage.service';
import { LogService } from '../../service/log.service';

@Component({
  selector: 'app-sign-in',
  templateUrl: './sign-in.component.html',
  styleUrls: ['./sign-in.component.less']
})
export class SignInComponent implements OnInit {

  signinForm: AbstractControl;

  constructor(private formBuilder: FormBuilder, private router: Router,
              private securitySrv: SecurityService, private storageSrv: StorageService, private logSrv: LogService) {
  }

  ngOnInit() {
    this.securitySrv.signOut();
    this.signinForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  get username() { return this.signinForm.get('username'); }
  get password() { return this.signinForm.get('password'); }


  onSubmit() {
    if (this.signinForm.valid) {
      this.logSrv.log('signinForm value = ', this.signinForm.value);
      const authReq = new AuthenticationRequest(this.username.value, this.password.value);

      // this.securitySrv.signIn(this.signinForm.value).subscribe(
      this.securitySrv.signIn(authReq).subscribe(
        response => {
          if (response.status === 200) {
            const authResp: AuthenticationResponse = response.data.result;
            this.storageSrv.setItem('token', authResp.jwtToken);
            this.storageSrv.setItem('currentUser', JSON.stringify(authResp.userDtls));

            this.router.navigate(['']);
          } else {
            this.logSrv.log('SignInComponent-response = ', response);
          }
        }
      );
    }
  }

}
