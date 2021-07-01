import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { map } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { LoginResponse } from './model/loginRsponse';

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  USER_NAME_SESSION_ATTRIBUTE_NAME = 'authenticatedUser'

  public usernameOrEmail: string;
  public password: string;
  private loginUrl = "http://localhost:8080/api/auth/signin";

  constructor(private http: HttpClient) {

  }

  authenticationService(usernameOrEmail: string, password: string): Observable<LoginResponse> {
    return this.http.post<LoginResponse>(`${this.loginUrl}`, {usernameOrEmail, password});
  }

  createBasicAuthToken(usernameOrEmail: string, password: string) {
    return 'Basic ' + window.btoa(usernameOrEmail + ":" + password)
  }

  registerSuccessfulLogin(usernameOrEmail: string, password: string) {
    sessionStorage.setItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME, usernameOrEmail)
  }

  logout() {
    sessionStorage.removeItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME);
    this.usernameOrEmail;
    this.password;
  }

  isUserLoggedIn() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return false
    return true
  }

  getLoggedInusernameOrEmail() {
    let user = sessionStorage.getItem(this.USER_NAME_SESSION_ATTRIBUTE_NAME)
    if (user === null) return ''
    return user
  }
}