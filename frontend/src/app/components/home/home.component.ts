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
  cantidadElementos = 4

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

    this.getEmprendimientos()
  }

  getEmprendimientos(page: number = 0) {
    this.pageNumber = page
    this.loading = true
    this.emprendimientosService
      .getEmprendimientos(
        page,
        this.cantidadElementos,
        this.categoriaBuscar,
        this.search
      )
      .subscribe(
        (res) => {
          this.loading = false
          if (res.data.emprendimientos)
            this.emprendimientos = res.data.emprendimientos.sort(
              () => Math.random() - 0.5
            )

          if (res.data.length)
            this.cantidadPaginas = Math.ceil(
              res.data.length / this.cantidadElementos
            )
        },
        (err) => {
          console.log(err)
        }
      )
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
