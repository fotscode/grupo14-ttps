import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Emprendimiento } from '../interfaces/Emprendimiento';
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

  getEmprendimientoByDomain(domainUrl:string){
    return this.http.get<EmprendimientoResponse>(this.URL+"/emprendimiento/get/domain/"+domainUrl)
  }

  getEmprendimientoWithJWT(){
    return this.http.get<EmprendimientoResponse>(this.URL+"/emprendimiento/get")
  }

  updateEmprendimiento(emprendimiento:Emprendimiento){
    return this.http.put<EmprendimientoResponse>(this.URL+"/emprendimiento/update",emprendimiento)
  }
}
