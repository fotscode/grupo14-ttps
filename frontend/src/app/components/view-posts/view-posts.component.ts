import { Component } from '@angular/core'
import { ActivatedRoute, Router } from '@angular/router'
import { Post } from 'src/app/interfaces/Post'
import { PostService } from 'src/app/services/post.service'

@Component({
  selector: 'app-view-posts',
  templateUrl: './view-posts.component.html',
  styleUrls: ['./view-posts.component.css'],
})
export class ViewPostsComponent {
  constructor(
    private postsService: PostService,
    private route: ActivatedRoute,
    private router: Router
  ) {}
  cantidadPaginas = 0
  paginaActual = 0
  cantidadElementos = 4
  private domainUrl: string = ''

  loading = true
  posts = [] as Post[]

  ngOnInit(): void {
    this.domainUrl = this.route.snapshot.paramMap.get('domain') || ''
    this.getPosts()
  }
  getPosts(page: number = 0) {
    this.loading = true
    this.paginaActual = page
    if (this.domainUrl) {
      this.postsService
        .getPostsByDomain(this.domainUrl, page, this.cantidadElementos)
        .subscribe((res) => {
          this.loading = false
          if (res.data.posts) this.posts = res.data.posts
          if (res.data.length)
            this.cantidadPaginas = Math.ceil(
              res.data.length / this.cantidadElementos
            )
        })
    }
  }
}
