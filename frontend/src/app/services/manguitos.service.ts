import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Manguito } from '../interfaces/Manguito';
import { ManguitosResponse } from '../interfaces/responses/ManguitosResponse';

@Injectable({
  providedIn: 'root'
})
export class ManguitosService {
  private URL = `${environment.baseApiUrl}/emprendimiento/manguito`

  constructor(private http: HttpClient) { }

  getManguitos(page: number, limit: number) {
    return this.http.get<ManguitosResponse>(`${this.URL}/list`, { params: { page, limit } })
  }

  saveManguito(manguito: Manguito, idEmp: number) {
    return this.http.post(`${this.URL}/save/${idEmp}`, manguito)
  }
}
