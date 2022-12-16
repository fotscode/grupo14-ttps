import { Component } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import { MatSnackBar } from '@angular/material/snack-bar'
import { ActivatedRoute, Router } from '@angular/router'
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
  loading: boolean = true

  constructor(
    private empService: EmprendimientosService,
    private route: ActivatedRoute,
    private dialog: MatDialog,
    private manguitoService: ManguitosService,
    private pagosService: PagosService,
    private matSnackBar: MatSnackBar,
    private router: Router
  ) {}

  ngOnInit(): void {
    const domainUrl = this.route.snapshot.paramMap.get('domain') || ''

    this.empService.getEmprendimientoByDomain(domainUrl).subscribe(
      (res) => {
        if (res.data.emprendimiento) {
          this.emprendimiento = res.data.emprendimiento
          this.emprendimiento.planes.sort((a, b) => a.monto - b.monto)
          this.emptyManguito.monto = this.emprendimiento.valorManguito
          this.loading = false
        }
      },
      (err) => {
        this.router.navigate(['/404'])
      }
    )
  }

  openDialog(): void {
    const dialogRef = this.dialog.open(DonateDialogComponent, {
      width: '80%',
      maxWidth: 800,
      data: { ...this.emptyManguito },
    })

    dialogRef.afterClosed().subscribe((result: Manguito) => {
      if (result && this.emprendimiento.id) {
        this.manguitoService
          .saveManguito(result, this.emprendimiento.id)
          .subscribe(
            (res) => {
              this.matSnackBar.open('Manguitos recibidos con éxito', void 0, {
                duration: 2000,
              })
            },
            (err) => {
              this.matSnackBar.open(
                'Hubo un error al enviar los manguitos',
                void 0,
                { duration: 2000 }
              )
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
      width: '90%',
      maxWidth: 800,
      data: { ...this.createPago(plan) },
    })

    dialogRef.afterClosed().subscribe((result: Pago) => {
      if (result && plan.id) {
        this.pagosService.savePago(result, plan.id).subscribe(
          (res) => {
            this.matSnackBar.open('Pago de plan recibido con éxito', void 0, {
              duration: 2000,
            })
          },
          (err) => {
            this.matSnackBar.open(
              'Hubo un error al enviar el pago del plan',
              void 0,
              { duration: 2000 }
            )
          }
        )
      }
    })
  }
}
