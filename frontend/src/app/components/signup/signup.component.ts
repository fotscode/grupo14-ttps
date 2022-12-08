import { Component } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { User } from 'src/app/interfaces/User'
import { AuthService } from 'src/app/services/auth.service'

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css'],
})
export class SignupComponent {
  constructor(
    private authService: AuthService,
    private router: Router,
    private matSnackBar: MatSnackBar
  ) {}

  user: User = {
    fullName: '',
    email: '',
    password: '',
  }

  repeatPassword = ''
  signUp() {
    if (this.user.password != this.repeatPassword) {
      this.matSnackBar.open('Las contraseñas no coinciden', void 0, {
        duration: 3000,
      })
      return
    }
    this.authService.signUp(this.user).subscribe(
      (res) => {
        this.matSnackBar.open('Se ha registrado correctamente', void 0, {duration: 3000})
        this.router.navigate(['/login'])
      },
      (err) => {
        this.matSnackBar.open("El mail ingresado fue registrado previamente", void 0, {duration: 3000})
      }
    )
  }
}
