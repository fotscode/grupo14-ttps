import { Component } from '@angular/core';
import { faSearch } from '@fortawesome/free-solid-svg-icons';
import { Emprendimiento } from 'src/app/interfaces/Emprendimiento';
import { EmprendimientosService } from 'src/app/services/emprendimientos.service';

@Component({
  selector: 'app-home-component',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent {
    faSearch=faSearch;
    emprendimientos: Emprendimiento[]=[]
    loading=true
    index=0

    constructor(private emprendimientosService: EmprendimientosService){}

    ngOnInit():void{
      this.emprendimientosService.getEmprendimientos().subscribe((res)=>{
        this.emprendimientos=res.data.emprendimientos
        console.log(res)
        this.loading=false
      },(err)=>{
        console.log(err)
      })
    }
}
