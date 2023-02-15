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
