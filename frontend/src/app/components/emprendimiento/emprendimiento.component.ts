import { Component } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { Categoria } from 'src/app/interfaces/Categoria'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { CategoriesService } from 'src/app/services/categories.service'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'

@Component({
  selector: 'app-emprendimiento-component',
  templateUrl: './emprendimiento.component.html',
  styleUrls: ['./emprendimiento.component.css'],
})
export class EmprendimientoComponent {
  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private emprendimientoService: EmprendimientosService,
    private categoriesService: CategoriesService
  ) {}

  categorias: Categoria[] = []
  emprendimiento = {} as Emprendimiento
  editMode: boolean = false
  loading: boolean = true

  ngOnInit(): void {
    this.categoriesService.getCategories().subscribe((res) => {
      if (res.data.categorias) this.categorias = res.data.categorias
    })
    this.emprendimientoService
      .getEmprendimientoWithJWT()
      .subscribe((res) => {
        this.loading = false
        if(res.data.emprendimiento)
          this.emprendimiento = res.data.emprendimiento
      })
  }

  isSelected(categoria: Categoria) {
    return (
      this.emprendimiento.categorias.filter((cat) => cat.id == categoria.id)
        .length > 0
    )
  }

  addCategoria(categoria: Categoria) {
    if (!this.editMode) return

    if (this.isSelected(categoria)) {
      this.emprendimiento.categorias = this.emprendimiento.categorias.filter(
        (cat) => cat.id != categoria.id
      )
    } else {
      this.emprendimiento.categorias.push(categoria)
    }
  }

  getCategorias() {
    if (this.editMode) {
      return this.categorias
    }
    return this.emprendimiento.categorias
  }

  isEditMode() {
    return this.editMode
  }

  toggleEditMode() {
    if (this.editMode) {
      this.emprendimientoService
        .updateEmprendimiento(this.emprendimiento)
        .subscribe((res) => {})
    }
    this.editMode = !this.editMode
  }

  cancelEdit() {
    this.editMode = false
    this.ngOnInit()
  }

  onFileSelected() {
    const inputNode: any = document.getElementById('enterpriseImg')
    if (typeof FileReader !== 'undefined') {
      const reader = new FileReader()

      reader.onload = (e: any) => {
        this.emprendimiento.imagen = this._arrayBufferToBase64(e.target.result)
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

  agregarRed() {
    this.emprendimiento.redesSociales.push({ nombre: '', url: '' })
  }

  hexToRgb(hex:string) {
    var result = /^#?([a-f\d]{2})([a-f\d]{2})([a-f\d]{2})$/i.exec(hex);
    return result ? {
      r: result[1],
      g: result[2], 
      b: result[3]
    } : null
  }

  setContrast(color:string){
    var m = color.substr(1).match(color.length == 7 ? /(\S{2})/g : /(\S{1})/g);
    if (m) {
      var r = parseInt(m[0], 16), g = parseInt(m[1], 16), b = parseInt(m[2], 16)
      const brightness = Math.round((r * 299 +
        (g * 587) +
        (b * 114)) / 1000);
      return (brightness > 125) ? 'black' : 'white';
    }
    return 'black'
  }

}
