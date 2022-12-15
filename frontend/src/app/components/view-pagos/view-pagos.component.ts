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
  manguitos = [] as Manguito[]
  columnasManguito = ['Nombre', 'Fecha', 'Cantidad', 'Monto']
  columnasPagosPlan = ['Nombre', 'Fecha', 'Monto', 'Mensaje', 'Contacto']
  manguitosVisible = true
  pagosPlan = [] as Pago[]
  loading:boolean=true
  constructor(
    private manguitosService: ManguitosService,
    private pagosService: PagosService
  ) {}

  ngOnInit(): void {
    this.manguitosService.getManguitos().subscribe((res: any) => {
      //TODO: fix this interface
      this.loading=false
      if (res.data.manguitos) this.manguitos = res.data.manguitos
    })
    this.pagosService.getPagos().subscribe((res: any) => {
      if (res.data.pagosPlanes) this.pagosPlan = res.data.pagosPlanes
    })
  }

  getFecha(fecha: Date): string {
    return new Date(fecha).toLocaleDateString()
  }

  toggleTable() {
    this.manguitosVisible = !this.manguitosVisible
  }
  
  isManguitosVisible(){
    return (this.manguitos.length>0)
  }

  isPlanesVisible(){
    return (this.pagosPlan.length>0)
  }
}
