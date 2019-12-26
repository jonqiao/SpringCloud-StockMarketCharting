import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, Validators } from '@angular/forms';
import { passSameValidator } from '../../../directive/password-same.directive';
import { User } from '../../../model/user.model';
import { SecurityService } from '../../../service/security.service';
import { LogService } from '../../../service/log.service';

@Component({
  selector: 'app-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.less']
})
export class ProfileComponent implements OnInit {

  profileForm = this.formBuilder.group({
    username: [{ value: '', disabled: true }, Validators.required],
    passGroup: this.formBuilder.group({
      password: [''],
      reptPassword: ['']
    }, { validator: passSameValidator }),
    email: ['', [Validators.required, Validators.email]],
    mobile: ['', [Validators.required, Validators.minLength(11), Validators.maxLength(11)]]
  });

  constructor(private formBuilder: FormBuilder, private router: Router, private route: ActivatedRoute,
              private securitySrv: SecurityService, private logSrv: LogService) { }

  ngOnInit() {
    this.logSrv.log('ProfileComponent ngOnInit = ');
    const username = this.securitySrv.getCurrentUser().username;
    this.securitySrv.getUserProfile(username).subscribe(
      response => {
        if (response.status === 200) {
          this.logSrv.log('getUserProfile-result = ', response.data.result);
          const user = response.data.result;
          this.profileForm.patchValue({
            username: user.username,
            email: user.email,
            mobile: user.mobile
          });
        }
      }
    );
  }

  get username() { return this.profileForm.get('username'); }
  get passGroup() { return this.profileForm.get('passGroup'); }
  get password() { return this.profileForm.get('passGroup').get('password'); }
  get reptPassword() { return this.profileForm.get('passGroup').get('reptPassword'); }
  get email() { return this.profileForm.get('email'); }
  get mobile() { return this.profileForm.get('mobile'); }


  onSubmit() {
    if (this.profileForm.valid) {
      this.logSrv.log('profileForm value = ', this.profileForm.value);
      const user = new User();
      user.username = this.username.value;
      if (this.password.value !== '') {
        user.password = this.password.value;
      } else {
        user.password = null;
      }
      user.role = 'USER';
      user.email = this.email.value;
      user.mobile = this.mobile.value;
      user.active = 'Y';

      this.securitySrv.updateUserProfile(user).subscribe(
        response => {
          if (response.status === 200) {
            this.logSrv.log('response = ', response.data.result);
            // when have relativeTo, the relateiveUrl is ['profile']
            // this.router.navigate(['../user'], {relativeTo: this.route, skipLocationChange: true});
            this.router.navigate(['user']); // If no starting route is provided, the navigation is absolute.
          }
        }
      );
    }
  }

}
