import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { environment } from 'src/environments/environment'
import { Pago } from '../interfaces/Pago'
import { PagosResponse } from '../interfaces/responses/PagosResponse'

@Injectable({
  providedIn: 'root',
})
export class PagosService {
  private URL = environment.baseApiUrl + '/emprendimiento/plan/pago'

  constructor(private http: HttpClient) { }

  getPagos(page: number, limit: number) {
    return this.http.get<PagosResponse>(`${this.URL}/list`, { params: { page, limit } })
  }

  savePago(pago: Pago, idPlan: number) {
    return this.http.post(`${this.URL}/save/${idPlan}`, pago)
  }
}
