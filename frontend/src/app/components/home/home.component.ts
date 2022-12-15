import { Component } from '@angular/core'
import { FormControl } from '@angular/forms'
import { faSearch } from '@fortawesome/free-solid-svg-icons'
import { Categoria } from 'src/app/interfaces/Categoria'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { CategoriesService } from 'src/app/services/categories.service'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'

@Component({
  selector: 'app-home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css'],
})
export class HomeComponent {
  faSearch = faSearch
  emprendimientos: Emprendimiento[] = []
  categorias: Categoria[] = []
  loading = true
  pageNumber = 0
  cantidadPaginas = 0
  search = ''
  categoriaBuscar = ''
  cantidadElementos=4

  constructor(
    private emprendimientosService: EmprendimientosService,
    private categoriesService: CategoriesService
  ) {}
  counter(i: number) {
    return new Array(i)
  }

  ngOnInit(): void {
    this.categoriesService.getCategories().subscribe((res) => {
      if (res.data.categorias) this.categorias = res.data.categorias
    })

    this.getEmprendimientos(0)
  }

  getEmprendimientos(page: number) {
    this.pageNumber = page
    this.emprendimientosService
      .getEmprendimientos(page, this.cantidadElementos, this.categoriaBuscar, this.search)
      .subscribe(
        (res) => {
          this.loading = false
          if (res.data.emprendimientos)
            this.emprendimientos = res.data.emprendimientos
        },
        (err) => {
          console.log(err)
        }
      )
    this.emprendimientosService
      .getEmprendimientosLength(this.categoriaBuscar, this.search)
      .subscribe((res: any) => {
        //TODO: fix
        this.cantidadPaginas = Math.ceil(res.data.cantidad / this.cantidadElementos)
      })
  }
}
