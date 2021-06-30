import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import {UserRegistrationModel} from './model/userRegistrationModel';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  public fullName: String;
  public password: String;
  public confirmPassword: String;
  public email: String;

  constructor(private http: HttpClient) { }
  private registerUrl = 'http://localhost:8080/api/auth/signup';

  registrationService(fullName: String, password: String, email: String, userName: String) {
    let userRegistrationPayload: UserRegistrationModel = new UserRegistrationModel(fullName, password, email, email)
    return this.http.post(`${this.registerUrl}`,userRegistrationPayload);
  }
}
