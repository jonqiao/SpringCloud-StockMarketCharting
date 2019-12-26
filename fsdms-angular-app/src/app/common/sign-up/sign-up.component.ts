import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormControl, Validators } from '@angular/forms';
import { passSameValidator } from '../../directive/password-same.directive';
import { User } from '../../model/user.model';
import { SecurityService } from '../../service/security.service';
import { LogService } from '../../service/log.service';

@Component({
  selector: 'app-sign-up',
  templateUrl: './sign-up.component.html',
  styleUrls: ['./sign-up.component.less']
})
export class SignUpComponent implements OnInit {

  constructor(private formBuilder: FormBuilder, private router: Router,
              private securitySrv: SecurityService, private logSrv: LogService) { }

  get username() { return this.signupForm.get('username'); }
  get passGroup() { return this.signupForm.get('passGroup'); }
  get password() { return this.signupForm.get('passGroup').get('password'); }
  get reptPassword() { return this.signupForm.get('passGroup').get('reptPassword'); }
  get email() { return this.signupForm.get('email'); }
  get mobile() { return this.signupForm.get('mobile'); }

  // solution 1: creating the instances manually.
  // signupForm = new FormGroup({
  //   username: new FormControl(''),
  //   passGroup: new FormGroup({
  //     password: new FormControl(''),
  //     reptPassword: new FormControl(''),
  //   }),
  //   email: new FormControl(''),
  //   mobile: new FormControl('')
  // });

  // solution 2: creating using the form builder
  signupForm = this.formBuilder.group({
    username: ['', Validators.required],
    passGroup: this.formBuilder.group({
      password: ['', Validators.required],
      reptPassword: ['', Validators.required]
    }, { validator: passSameValidator }),
    email: ['', [Validators.required, Validators.email]],
    mobile: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(11)]]
  });

  ngOnInit() { }


  onSubmit() {
    if (this.signupForm.valid) {
      this.logSrv.log('signupForm value = ', this.signupForm.value);
      const user = new User();
      user.username = this.username.value;
      user.password = this.password.value;
      user.role = 'USER';
      user.email = this.email.value;
      user.mobile = this.mobile.value;
      user.active = 'N';

      this.securitySrv.signUp(user).subscribe(
        response => {
          if (response.status === 200) {
            this.logSrv.log('response = ', response.data.result);
            this.router.navigate(['signin']);
          }
        }
      );
    }
  }
}
