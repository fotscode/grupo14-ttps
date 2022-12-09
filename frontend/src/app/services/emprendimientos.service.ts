import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { EmprendimientoResponse } from '../interfaces/responses/EmprendimientosResponse';

@Injectable({
  providedIn: 'root'
})
export class EmprendimientosService {
  private URL = environment.baseApiUrl
  constructor(private http:HttpClient) { }

  getEmprendimientos(){
    return this.http.get<EmprendimientoResponse>(this.URL+"/emprendimiento/list")
  }
  getEmprendimiento(id:number){
    return this.http.get<EmprendimientoResponse>(this.URL+"/emprendimiento/get/"+id)
  }
}
