import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environments/environment';
import { Categoria } from '../interfaces/Categoria';
import { CategoriasResponse } from '../interfaces/responses/CategoriasResponse';

@Injectable({
  providedIn: 'root'
})
export class CategoriesService {
  private URL = environment.baseApiUrl

  constructor(private http: HttpClient) { }

  getCategories(){
    return this.http.get<CategoriasResponse>(this.URL+"/categoria/list")
  }

  saveCategories(category:Categoria){
    return this.http.put<CategoriasResponse>(this.URL+"/categoria/update",category)
  }

  deleteCategory(id:number){
    return this.http.delete<CategoriasResponse>(this.URL+"/categoria/delete/"+id)
  }


}
