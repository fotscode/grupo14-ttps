import { Component } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import { ActivatedRoute } from '@angular/router'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { Manguito } from 'src/app/interfaces/Manguito'
import { Pago } from 'src/app/interfaces/Pago'
import { Plan } from 'src/app/interfaces/Plan'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'
import { ManguitosService } from 'src/app/services/manguitos.service'
import { PagosService } from 'src/app/services/pagos.service'
import { DonateDialogComponent } from '../donate-dialog/donate-dialog.component'
import { DonatePlanDialogComponent } from '../donate-plan-dialog/donate-plan-dialog.component'

@Component({
  selector: 'app-donate',
  templateUrl: './donate.component.html',
  styleUrls: ['./donate.component.css'],
})
export class DonateComponent {
  emprendimiento = {} as Emprendimiento
  cantManguitos = 3

  emptyManguito: Manguito = {
    nombrePersona: '',
    fecha: new Date(),
    cantidad: this.cantManguitos,
    monto: 0,
    mensaje: '',
  }

  constructor(
    private empService: EmprendimientosService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private manguitoService: ManguitosService,
    private pagosService: PagosService
  ) {}

  ngOnInit(): void {
    const domainUrl = this.route.snapshot.paramMap.get('domain')
    if (domainUrl)
      this.empService.getEmprendimientoByDomain(domainUrl).subscribe((res) => {
        if (res.data.emprendimiento) {
          this.emprendimiento = res.data.emprendimiento
          this.emprendimiento.planes.sort((a, b) => a.monto - b.monto)
          this.emptyManguito.monto = this.emprendimiento.valorManguito
        }
      })
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DonateDialogComponent, {
      width: '50%',
      data: { ...this.emptyManguito },
    })

    dialogRef.afterClosed().subscribe((result: Manguito) => {
      if (result && this.emprendimiento.id) {
        this.manguitoService
          .saveManguito(result, this.emprendimiento.id)
          .subscribe(
            (res) => {
              console.log(res)
            },
            (err) => {
              console.log(err)
            }
          )
      }
    })
  }

  createPago(plan: Plan): Pago {
    return {
      nombrePersona: '',
      contacto: '',
      fecha: new Date(),
      monto: plan.monto,
      mensaje: '',
    }
  }
  openPlanDialog(plan: Plan) {
    const dialogRef = this.dialog.open(DonatePlanDialogComponent, {
      width: '50%',
      data: { ...this.createPago(plan) },
    })

    dialogRef.afterClosed().subscribe((result: Pago) => {
      if (result && plan.id) {
        this.pagosService.savePago(result, plan.id).subscribe(
          (res) => {
            console.log(res)
          },
          (err) => {
            console.log(err)
          }
        )
      }
    })
  }
}
