import { Component } from '@angular/core'
import { ActivatedRoute } from '@angular/router'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { Plan } from 'src/app/interfaces/Plan'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'

@Component({
  selector: 'app-edit-pagos',
  templateUrl: './edit-pagos.component.html',
  styleUrls: ['./edit-pagos.component.css'],
})
export class EditPagosComponent {
  emprendimiento = {} as Emprendimiento
  constructor(
    private route: ActivatedRoute,
    private empService: EmprendimientosService
  ) {}
  ngOnInit(): void {
    const domainUrl = this.route.snapshot.paramMap.get('domain')
    if (domainUrl) {
      this.empService.getEmprendimientoByDomain(domainUrl).subscribe((res) => {
        if (res.data.emprendimiento)
          this.emprendimiento = res.data.emprendimiento
        console.log(this.emprendimiento)
      })
    }
  }

  agregarPlan() {
    this.emprendimiento.planes.push({
      titulo: '',
      descripcion: '',
      monto: 0,
      pagos: [],
    })
  }

  guardarCambios() {
    this.empService
      .updateEmprendimiento(this.emprendimiento)
      .subscribe((res) => {
        console.log(res)
        if (res.data.emprendimiento)
          this.emprendimiento = res.data.emprendimiento
      })
  }

  eliminarPlan(plan: Plan) {
    this.emprendimiento.planes = this.emprendimiento.planes.filter(
      (p) => p != plan
    )
  }
}
