import { User } from './user.model';
import { ApiResponse } from './api.response';

export class AuthenticationResponse {
  jwtToken: string;
  userDtls: User;
}

// {
//   "status": 200,
//   "msg": "OK",
//   "data": {
//       "result": {
//           "jwtToken": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VybmFtA",
//           "userDtls": {
//               "username": "username1",
//               "password": null,
//               "role": "ROLE_USER",
//               "email": null,
//               "mobile": null,
//               "active": null
//           }
//       }
//   }
// }
