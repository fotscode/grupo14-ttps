import { Injectable } from '@angular/core'
import {
  HttpEvent,
  HttpHandler,
  HttpInterceptor,
  HttpRequest,
} from '@angular/common/http'
import {
  Observable,
  catchError,
  BehaviorSubject,
  switchMap,
  take,
  filter,
  throwError,
} from 'rxjs'
import { AuthService } from '../services/auth.service'
import { TokenResponse } from '../interfaces/responses/TokenResponse'

@Injectable({
  providedIn: 'root',
})
export class TokenInterceptorService implements HttpInterceptor {
  constructor(private authService: AuthService) {}
  private isRefreshing = false
  private refreshTokenSubject: BehaviorSubject<any> = new BehaviorSubject<any>(
    null
  )

  intercept(
    req: HttpRequest<any>,
    next: HttpHandler
  ): Observable<HttpEvent<any>> {
    const token = this.authService.getToken()
    if (token) {
      const cloned = req.clone({
        headers: req.headers.set('Authorization', 'Bearer ' + token),
      })
      return next.handle(cloned).pipe(
        catchError((err) => {
          if (err.status === 401) {
            return this.handle401Error(req, next)
          }
          return throwError(err)
        })
      )
    } else return next.handle(req)
  }

  private handle401Error(req: HttpRequest<any>, next: HttpHandler) {
    if (!this.isRefreshing) {
      this.isRefreshing = true
      this.refreshTokenSubject.next(null)

      const token = this.authService.getRefreshToken()
      console.log(token)

      if (token) {
        return this.authService.refreshAccess(token).pipe(
          switchMap((res: TokenResponse) => {
            this.isRefreshing = false
            this.refreshTokenSubject.next(res.data.tokens.access_token)
            this.authService.setTokens(res.data.tokens.access_token, res.data.tokens.refresh_token)
            const cloned = req.clone({
              headers: req.headers.set('Authorization', 'Bearer ' + res.data.tokens.access_token),
            })
            return next.handle(cloned)
          }),
          catchError((err) => {
            this.isRefreshing = false
            this.authService.logOut()
            return throwError(err)
          })
        )
      }
    }

    return this.refreshTokenSubject.pipe(
      filter((token) => token != null),
      take(1),
      switchMap((token) =>
        next.handle(
          req.clone({
            headers: req.headers.set('Authorization', 'Bearer ' + token),
          })
        )
      )
    )
  }
}
