import { Injectable } from '@angular/core'
import { CanActivate, Router } from '@angular/router'
import { Observable } from 'rxjs'
import { RoleResponse } from '../interfaces/responses/RoleResponse'
import { Role } from '../interfaces/Role'
import { AuthService } from '../services/auth.service'

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(private authService: AuthService, private router: Router) {}
  canActivate(): Observable<boolean> {
    return this.authService.getRoles().subscribe((res:RoleResponse) => {
      if (res.data.roles.find((role:Role) => role.name === 'ROLE_ADMIN')) {
        return true
      }
      this.router.navigate(['/Home'])
      return false
    })
  }
}
