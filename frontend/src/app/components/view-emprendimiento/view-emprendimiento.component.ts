import { Component } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento'
import { EmprendimientosService } from 'src/app/services/emprendimientos.service'

@Component({
  selector: 'app-view-emprendimiento',
  templateUrl: './view-emprendimiento.component.html',
  styleUrls: ['./view-emprendimiento.component.css'],
})
export class ViewEmprendimientoComponent {
  loading = true
  emprendimiento = {} as Emprendimiento

  constructor(
    private emprendimientoService: EmprendimientosService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const domainUrl = this.route.snapshot.paramMap.get('domain') || ''
    this.emprendimientoService.getEmprendimientoByDomain(domainUrl).subscribe(
      (res) => {
        if (res.data.emprendimiento)
          this.emprendimiento = res.data.emprendimiento
        this.loading = false
      },
      (err) => {
        this.router.navigate(['/404'])
      }
    )
  }
}
