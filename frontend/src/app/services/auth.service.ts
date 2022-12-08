import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'
import { Router } from '@angular/router'
import * as moment from 'moment'
import { environment } from 'src/environments/environment'
import { LoginUser } from '../interfaces/LoginUser';
import { TokenResponse } from '../interfaces/responses/TokenResponse';
import { User } from '../interfaces/User';
import { SignupResponse } from '../interfaces/responses/SignupResponse';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  private URL = environment.baseApiUrl
  constructor(private http: HttpClient, private router: Router) {}

  signUp(user: User) {
    return this.http.post<SignupResponse>(this.URL + '/user/save', user)
  }

  signIn(user: LoginUser) {
    return this.http.post<TokenResponse>(this.URL + '/login', user)
  }

  loggedIn(): Boolean {
    return !!localStorage.getItem('access_token')
  }

  getToken() {
    return localStorage.getItem('access_token')
  }

  logOut() {
    localStorage.removeItem('access_token')
    localStorage.removeItem('refresh_token')
    this.router.navigate(['/login'])
  }
}
