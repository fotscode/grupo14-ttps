import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { environment } from 'src/environments/environment'
import { Emprendimiento } from '../interfaces/Emprendimiento'
import { EmprendimientoResponse } from '../interfaces/responses/EmprendimientosResponse'

@Injectable({
  providedIn: 'root',
})
export class EmprendimientosService {
  private URL = `${environment.baseApiUrl}/emprendimiento`
  constructor(private http: HttpClient) {}

  getEmprendimientos(
    page: number,
    limit: number = 2,
    category: string = '',
    search: string = ''
  ) {
    return this.http.get<EmprendimientoResponse>(this.URL + '/list', {
      params: { page, limit, category, search },
    })
  }
  getEmprendimiento(id: number) {
    return this.http.get<EmprendimientoResponse>(this.URL + '/get/' + id)
  }

  getEmprendimientoByDomain(domainUrl: string) {
    return this.http.get<EmprendimientoResponse>(
      this.URL + '/get/domain/' + domainUrl
    )
  }

  getEmprendimientoWithJWT() {
    return this.http.get<EmprendimientoResponse>(this.URL + '/get')
  }

  updateEmprendimiento(emprendimiento: Emprendimiento) {
    return this.http.put<EmprendimientoResponse>(
      this.URL + '/update',
      emprendimiento
    )
  }

  getEmprendimientosLength(category: string = '', search: string = '') {
    return this.http.get<EmprendimientoResponse>(this.URL + '/list/size', {
      params: { category, search },
    })
  }
}
