import { Component } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { AuthService } from 'src/app/services/auth.service'

@Component({
  selector: 'app-navigation-header',
  templateUrl: './navigation-header.component.html',
  styleUrls: ['./navigation-header.component.css'],
})
export class NavigationHeaderComponent {
  constructor(
    private authService: AuthService,
    private snackBar: MatSnackBar
  ) {}

  wasLoggedIn: Boolean = false
  isAdmin: Boolean = false

  isLogged() {
    return this.authService.loggedIn()
  }

  logOut() {
    this.authService.logOut()
    this.snackBar.open('Se ha cerrado sesion', void 0, { duration: 3000 })
    this.wasLoggedIn = false
    this.isAdmin = false
  }

  ngOnInit(): void {}

  checkAdmin() {
    if (this.wasLoggedIn != this.isLogged()) {
      this.wasLoggedIn = this.isLogged()
      this.authService.getRoles().subscribe((res: any) => {
        this.isAdmin =
          res.data.roles.filter((r: any) => r.name == 'ROLE_ADMIN').length > 0
      })
    }
    return true
  }
}
