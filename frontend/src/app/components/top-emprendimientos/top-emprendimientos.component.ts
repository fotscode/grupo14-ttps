import { Component } from '@angular/core'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'

@Component({
  selector: 'app-top-emprendimientos',
  templateUrl: './top-emprendimientos.component.html',
  styleUrls: ['./top-emprendimientos.component.css'],
})
export class TopEmprendimientosComponent {
  constructor(private emprendimientoService: EmprendimientosService) {}
  topManguitos = [] as Emprendimiento[]
  topDonaciones = [] as Emprendimiento[]
  loading = true
  colors = ['#11c8d9', '#00c78b', '#cfab1d', '#bfbfbf', '#9e8a5a']
  manguitosVisible = true

  cantidad(i: number) {
    return this.manguitosVisible
      ? 'Manguitos: '+this.topManguitos[i].manguitos
          .map((m) => m.cantidad)
          .reduce((a, b) => a + b)
      : 'Donaciones: '+this.topDonaciones[i].planes.map((p) =>
          p.pagos.flatMap((p) => p.monto).reduce((a, b) => a + b)
        ).reduce((a, b) => a + b)+'$'
  }

  ngOnInit(): void {
    this.emprendimientoService.getTopEmprendimientos().subscribe((res) => {
      this.topManguitos = res.data.topManguitos.reverse()
      this.topDonaciones = res.data.topDonaciones.reverse()
      this.loading = false
      console.log(res.data.topManguitos)
      console.log(res.data.topDonaciones)
    })
  }

  toggleVisible() {
    this.manguitosVisible = !this.manguitosVisible
  }

  getEmprendimientos() {
    return this.manguitosVisible ? this.topManguitos : this.topDonaciones
  }
}
