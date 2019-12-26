import { FormGroup, FormControl } from '@angular/forms';

export function mobileValidator(control: FormControl): any {
  const val = control.value;
  const mobieReg = /^(((13[0-9]{1})|(15[0-9]{1})|(18[0-9]{1}))+\d{8})$/;
  const valid = mobieReg.test(val);
  return valid ? null : { mobileCheck: { invalid: 'mobile number format is not correct!!' } };
}

export function passSameValidator(controlGroup: FormGroup): any {
  const password = controlGroup.get('password').value as FormControl;
  const reptPassword = controlGroup.get('reptPassword').value as FormControl;
  const valid: boolean = (password === reptPassword);
  return valid ? null : { passSame: { info: 'password is not same!!' } };
}
