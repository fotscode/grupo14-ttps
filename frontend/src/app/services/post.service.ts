import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { environment } from 'src/environments/environment'
import { Post } from '../interfaces/Post'
import { PostResponse } from '../interfaces/responses/PostResponse'

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private URL = `${environment.baseApiUrl}/emprendimiento/post/`
  constructor(private http: HttpClient) {}

  getPosts(page: number, limit: number) {
    return this.http.get<PostResponse>(this.URL + 'list/', {
      params: { page, limit },
    })
  }

  getPostsByDomain(domain: string, page: number, limit: number) {
    return this.http.get<PostResponse>(this.URL + 'list/' + domain, {
      params: { page, limit },
    })
  }

  deletePost(id: number) {
    return this.http.delete<PostResponse>(this.URL + 'delete/' + id)
  }

  updatePost(post: Post) {
    return this.http.put<PostResponse>(this.URL + 'update/', post)
  }
}
