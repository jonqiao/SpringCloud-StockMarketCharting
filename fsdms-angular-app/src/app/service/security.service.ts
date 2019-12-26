import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environments/environment';
import { ApiResponse } from '../model/api.response';
import { Observable } from 'rxjs/index';
import { User } from '../model/user.model';
import { AuthenticationRequest } from '../model/auth.request';
import { StorageService } from './storage.service';

@Injectable({
  providedIn: 'root'
})
export class SecurityService {

  constructor(private http: HttpClient, private storageSrv: StorageService) { }

  baseUrl: string = environment.getBaseUrl('scs');

  signUp(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'user/signup', user);
  }

  signIn(authReq: AuthenticationRequest): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'security/signin', authReq);
  }

  getUserProfile(username: string): Observable<ApiResponse> {
    if (!username) { return new Observable(); }
    return this.http.get<ApiResponse>(this.baseUrl + 'user/' + username);
  }

  updateUserProfile(user: User): Observable<ApiResponse> {
    return this.http.post<ApiResponse>(this.baseUrl + 'user/profile', user);
  }

  authAdmin(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 'security/admin');
  }

  authenticated(): Observable<ApiResponse> {
    return this.http.get<ApiResponse>(this.baseUrl + 'security/authenticated');
  }

  getCurrentUser(): User {
    const userStr = this.storageSrv.getItem('currentUser');
    return userStr ? JSON.parse(userStr) : null;
  }

  getCurrentUserRole(): string {
    const currentUser: User = this.getCurrentUser();
    return currentUser ? currentUser.role.split('_')[1] : '';
  }

  hasRole(role: string): boolean {
    const currentUser: User = this.getCurrentUser();
    if (!currentUser) {
      return false;
    }
    const currentUserRole = currentUser.role;
    return currentUserRole.indexOf('ROLE_' + role.toUpperCase()) !== -1;
  }

  isLoggedIn(): boolean {
    const token = this.storageSrv.getItem('token');
    const currentUser = this.getCurrentUser();

    if (token && token.length > 0 && currentUser && currentUser.username) {
      return true;
    } else {
      return false;
    }
  }

  signOut(): void {
    this.storageSrv.removeItem('token');
    this.storageSrv.removeItem('currentUser');
    this.storageSrv.clear();
  }

}
