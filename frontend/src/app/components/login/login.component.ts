import { Component, Inject, OnInit } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { LoginUser } from 'src/app/interfaces/LoginUser'
import { AuthService } from 'src/app/services/auth.service'

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  public constructor(
    private authService: AuthService,
    private router: Router,
    private snackBar: MatSnackBar
  ) {}

  user: LoginUser = {
    email: '',
    password: '',
  }

  logIn() {
    this.authService.signIn(this.user).subscribe(
      (res) => {
        localStorage.setItem('access_token', res.data.tokens.access_token)
        localStorage.setItem('refresh_token', res.data.tokens.refresh_token)
        this.snackBar.open('Se ha iniciado sesion', void 0, { duration: 3000 })
        this.router.navigate(['/Home'])
      },
      (err) => {
        this.snackBar.open('El email o contraseña es incorrecto', void 0, {
          duration: 3000,
        })
        this.user.password = ''
      }
    )
  }
  ngOnInit(): void {}
}
