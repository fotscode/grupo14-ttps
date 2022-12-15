import { Injectable } from '@angular/core'
import { HttpClient, HttpHeaders } from '@angular/common/http'
import { Router } from '@angular/router'
import { environment } from 'src/environments/environment'
import { LoginUser } from '../interfaces/LoginUser'
import { TokenResponse } from '../interfaces/responses/TokenResponse'
import { User } from '../interfaces/User'
import { SignupResponse } from '../interfaces/responses/SignupResponse'
import { RoleResponse } from '../interfaces/responses/RoleResponse'

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' }),
  }
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

  getRefreshToken() {
    return localStorage.getItem('refresh_token')
  }
  
  setTokens(token: string, refresh_token: string) {
    localStorage.setItem('access_token', token)
    localStorage.setItem('refresh_token', refresh_token)
  }

  logOut() {
    localStorage.removeItem('access_token')
    localStorage.removeItem('refresh_token')
    this.router.navigate(['/login'])
  }

  isExpired() {
    let x = this.getToken()?.split('.')[1]
    let payload = x ? JSON.parse(atob(x)) : null
    return payload?.exp < Date.now() / 1000
  }

  refreshAccess(token: string) {
    return this.http.post<TokenResponse>(
      this.URL + '/token/refresh',
      {
        refresh_token: token,
      },
      this.httpOptions
    )
  }

  getRoles(): any {
    return this.http.get<RoleResponse>(this.URL + '/user/roles')
  }
}
