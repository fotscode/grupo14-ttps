import { Component } from '@angular/core'
import { FormArray, FormBuilder, FormGroup } from '@angular/forms'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Router } from '@angular/router'
import { Categoria } from 'src/app/interfaces/Categoria'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { RedSocial } from 'src/app/interfaces/RedSocial'
import { User } from 'src/app/interfaces/User'
import { AuthService } from 'src/app/services/auth.service'
import { CategoriesService } from 'src/app/services/categories.service'

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
    private categoriesService: CategoriesService
  ) {}

  redes: RedSocial[] = [
    {
      nombre: '',
      url: '',
    },
  ]

  categorias: Categoria[] = []

  user: User = {
    fullName: '',
    email: '',
    password: '',
    emprendimiento: {
      domainUrl: '',
      nombre: '',
      descripcion: '',
      valorManguito: 100,
      planes: [],
      filterByDonations: true,
      filterByManguitos: true,
      imagen: '',
      categorias: [] as Categoria[],
      posts: [],
      redesSociales: [] as RedSocial[],
      manguitos: [],
    },
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
        this.matSnackBar.open('Se ha registrado correctamente', void 0, {
          duration: 3000,
        })
        this.router.navigate(['/login'])
      },
      (err) => {
        this.matSnackBar.open(
          'El mail ingresado fue registrado previamente',
          void 0,
          { duration: 3000 }
        )
      }
    )
  }
  newRedSocial(): RedSocial {
    return {
      nombre: '',
      url: '',
    }
  }
  addRedSocial() {
    this.user.emprendimiento.redesSociales.push(this.newRedSocial())
  }
  removeRedSocial(i: number) {
    this.user.emprendimiento.redesSociales.splice(i, 1)
  }

  onFileSelected() {
    const inputNode: any = document.getElementById('enterpriseImg')
    if (typeof FileReader !== 'undefined') {
      const reader = new FileReader()

      reader.onload = (e: any) => {
        this.user.emprendimiento.imagen = this._arrayBufferToBase64(
          e.target.result
        )
      }
      reader.readAsArrayBuffer(inputNode.files[0])
    }
  }

  _arrayBufferToBase64(buffer: ArrayBuffer) {
    var binary = ''
    var bytes = new Uint8Array(buffer)
    var len = bytes.byteLength
    for (var i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i])
    }
    return window.btoa(binary)
  }

  ngOnInit(): void {
    this.categoriesService.getCategories().subscribe((res) => {
      this.categorias = res.data.categorias
    })
  }

  isSelected(categoria: Categoria) {
    return this.user.emprendimiento.categorias.includes(categoria)
  }

  addCategoria(categoria: Categoria) {
    if (this.isSelected(categoria)) {
      this.user.emprendimiento.categorias =
        this.user.emprendimiento.categorias.filter(
          (cat) => cat.id != categoria.id
        )
    } else {
      this.user.emprendimiento.categorias.push(categoria)
    }
  }
}
