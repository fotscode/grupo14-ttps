import { Component } from '@angular/core'
import { MatDialog, MatDialogConfig } from '@angular/material/dialog'
import { MatSnackBar } from '@angular/material/snack-bar'
import { ActivatedRoute } from '@angular/router'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { Plan } from 'src/app/interfaces/Plan'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'
import { TemplateDialogComponent } from '../template-dialog/template-dialog.component'

@Component({
  selector: 'app-edit-pagos',
  templateUrl: './edit-pagos.component.html',
  styleUrls: ['./edit-pagos.component.css'],
})
export class EditPagosComponent {
  emprendimiento = {} as Emprendimiento
  constructor(
    public popup:MatDialog,
    private route: ActivatedRoute,
    private empService: EmprendimientosService,
    private matSnackBar: MatSnackBar
  ) {}

  loading: boolean = true

  ngOnInit(): void {
    this.empService.getEmprendimientoWithJWT().subscribe((res) => {
      this.loading = false
      if (res.data.emprendimiento) this.emprendimiento = res.data.emprendimiento
    })
  }

  agregarPlan() {
    this.emprendimiento.planes.push({
      titulo: '',
      descripcion: '',
      monto: 1,
      pagos: [],
    })
  }

  guardarCambios() {
    this.empService.updateEmprendimiento(this.emprendimiento).subscribe(
      (res) => {
        if (res.data.emprendimiento)
          this.emprendimiento = res.data.emprendimiento
          this.matSnackBar.open('Se guardaron los cambios', void 0, {
          duration: 3000,
        })
      },
      (err) => {
        this.matSnackBar.open('Ocurrio un error', void 0, { duration: 3000 })
      }
    )
  }

  eliminarPlanAttempt(plan:Plan){
    const dialogConfig=new MatDialogConfig()
    dialogConfig.disableClose=true
    dialogConfig.autoFocus=false
    dialogConfig.width='50%'
    dialogConfig.data={
      title:'Eliminar Plan',
      dialog:'eliminar el plan'
    }
    const reference=this.popup.open(TemplateDialogComponent,dialogConfig)
    reference.afterClosed().subscribe((res)=>{
      if(res){
        this.eliminarPlan(plan)
      }
    })
  }

  eliminarPlan(plan: Plan) {
    this.emprendimiento.planes = this.emprendimiento.planes.filter(
      (p) => p != plan
    )
  }
}
