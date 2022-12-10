import { HttpClient } from '@angular/common/http'
import { Injectable } from '@angular/core'
import { environment } from 'src/environments/environment'
import { Post } from '../interfaces/Post'
import { PostResponse } from '../interfaces/responses/PostResponse'

@Injectable({
  providedIn: 'root',
})
export class PostService {
  private URL = environment.baseApiUrl
  constructor(private http: HttpClient) {}

  getPosts() {
    return this.http.get<PostResponse>(this.URL + '/emprendimiento/post/list/')
  }

  deletePost(id: number) {
    return this.http.delete<PostResponse>(this.URL + '/emprendimiento/post/delete/' + id)
  }

  updatePost(post: Post) {
    return this.http.put<PostResponse>(this.URL + '/emprendimiento/post/update/', post)
  }
}
