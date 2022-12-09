import { Component, OnInit } from '@angular/core'
import { Post } from 'src/app/interfaces/Post'
import { PostService } from 'src/app/services/post.service'

@Component({
  selector: 'app-edit-posts-component',
  templateUrl: './edit-posts-component.component.html',
  styleUrls: ['./edit-posts-component.component.css'],
})
export class EditPostsComponentComponent implements OnInit {
  posts: Post[] = [
    {
      id: 1,
      titulo: 'Post 1',
      descripcion: 'Contenido del post 1',
      fotos: [{ id: 1, image: 'https://picsum.photos/200/300' }],
    },
  ]
  loading = true

  index = 0
  constructor(private postService: PostService) {}

  ngOnInit(): void {
    this.postService.getPosts().subscribe((res) => {
      this.posts = res.data.posts
      this.loading = false
    })
  }

  deletePost(id: number | undefined) {
    if (!id) return
    this.postService.deletePost(id).subscribe((res) => {
      this.posts = this.posts.filter((post) => post.id !== id)
    })
  }
}
