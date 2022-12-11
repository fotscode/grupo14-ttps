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

  ngOnInit(): void {
    this.categoriesService.getCategories().subscribe((res) => {
      this.categorias = res.data.categorias
    })
    const domainUrl = this.route.snapshot.paramMap.get('domain')
    if (domainUrl) {
      this.emprendimientoService
        .getEmprendimientoByDomain(domainUrl)
        .subscribe((res: any) => {
          this.emprendimiento = res.data.emprendimiento
        })
    }

  }

  isSelected(categoria: Categoria) {
    return this.emprendimiento.categorias.filter((cat)=>cat.id==categoria.id).length>0
  }

  addCategoria(categoria: Categoria) {
    if (!this.editMode) return

    if (this.isSelected(categoria)) {
      this.emprendimiento.categorias =
        this.emprendimiento.categorias.filter(
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
    if(this.editMode){
      this.emprendimientoService.updateEmprendimiento(this.emprendimiento).subscribe((res)=>{
        this.router.navigate(["/emprendimiento",this.emprendimiento.domainUrl])
      })
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
        this.emprendimiento.imagen = this._arrayBufferToBase64(
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

  agregarRed(){
    this.emprendimiento.redesSociales.push({nombre:"",url:""})
  }
}
