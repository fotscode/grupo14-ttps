import { Component } from '@angular/core'
import { FormArray, FormBuilder, FormGroup } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { RedSocial } from 'src/app/interfaces/RedSocial'
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
    private matSnackBar: MatSnackBar,
    private fb:FormBuilder
  ) {}

  redes: RedSocial[]=[{
    nombre:'',
    url:''
  }]

  user: User = {
    fullName: '',
    email: '',
    password: '',
    emprendimiento:{
      domainUrl:'',
      nombre:'',
      descripcion:'',
      valorManguito: 100,
      planes:[],
      filterByDonations:true,
      filterByManguitos:true,
      imagen:'',
      categorias:[],
      posts:[],
      redesSociales:[],
      manguitos:[]
    }
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
  newRedSocial(){
    return {
      nombre:'',
      url:''
    }
  }
  addRedSocial(){
    this.user.emprendimiento.redesSociales.push(this.newRedSocial())
  }
  removeRedSocial(i:number){
    this.user.emprendimiento.redesSociales.splice(i,1)
  }
}
