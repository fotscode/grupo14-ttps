import { Component } from '@angular/core'
import { Manguito } from 'src/app/interfaces/Manguito'
import { Pago } from 'src/app/interfaces/Pago'
import { ManguitosService } from 'src/app/services/manguitos.service'
import { PagosService } from 'src/app/services/pagos.service'

@Component({
  selector: 'app-view-pagos',
  templateUrl: './view-pagos.component.html',
  styleUrls: ['./view-pagos.component.css'],
})
export class ViewPagosComponent {
  manguitosVisible = true
  cantidadElementos = 14

  columnasManguito = ['Nombre', 'Fecha', 'Cantidad', 'Monto']
  manguitos = [] as Manguito[]
  loadingManguitos: boolean = true
  paginaActualManguitos = 0
  cantidadPaginasManguitos = 0

  columnasPagosPlan = ['Nombre', 'Fecha', 'Monto', 'Mensaje', 'Contacto']
  pagosPlan = [] as Pago[]
  loadingPagosPlan: boolean = true
  paginaActualPagos = 0
  cantidadPaginasPagos = 0
  
  constructor(
    private manguitosService: ManguitosService,
    private pagosService: PagosService
  ) {}

  ngOnInit(): void {
    this.getManguitos()
    this.getPagosPlan()
  }

  getManguitos(page: number = 0) {
    this.paginaActualManguitos = page
    this.loadingManguitos = true
    this.manguitosService
      .getManguitos(page, this.cantidadElementos)
      .subscribe((res: any) => {
        //TODO: fix this interface
        this.loadingManguitos = false
        if (res.data.manguitos) this.manguitos = res.data.manguitos
        if (res.data.length)
          this.cantidadPaginasManguitos = Math.ceil(
            res.data.length / this.cantidadElementos
          )
      })
  }

  getPagosPlan(page: number = 0) {
    this.paginaActualPagos = page
    this.loadingPagosPlan = true
    this.pagosService
      .getPagos(page, this.cantidadElementos)
      .subscribe((res: any) => {
        this.loadingPagosPlan = false
        if (res.data.pagosPlanes) this.pagosPlan = res.data.pagosPlanes
        if (res.data.length)
          this.cantidadPaginasPagos = Math.ceil(
            res.data.length / this.cantidadElementos
          )
      })
  }

  getFecha(fecha: Date): string {
    return new Date(fecha).toLocaleDateString()
  }

  toggleTable() {
    this.manguitosVisible = !this.manguitosVisible
  }

  isManguitosVisible() {
    return this.manguitos.length > 0
  }

  isPlanesVisible() {
    return this.pagosPlan.length > 0
  }
}
