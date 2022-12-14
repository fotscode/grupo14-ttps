import { Injectable } from '@angular/core'
import {
  ActivatedRouteSnapshot,
  CanActivate,
  Router,
  RouterStateSnapshot,
  UrlTree,
} from '@angular/router'
import { Observable,map } from 'rxjs'
import { RoleResponse } from '../interfaces/responses/RoleResponse'
import { AuthService } from '../services/auth.service'
import { AdminGuard } from './admin.guard'

@Injectable({
  providedIn: 'root',
})
export class NegateAdminGuard implements CanActivate {
  constructor(private authService: AuthService, private router:Router) {}
  canActivate(): Observable<boolean> {
    return this.authService.getRoles().subscribe((res:RoleResponse) => {
      if (res.data.roles.find((role) => role.name === 'ROLE_ADMIN')) {
        this.router.navigate(['/Home'])
        return false
      }
      return true
    })
  }
}
