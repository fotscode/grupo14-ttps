import { Component, OnInit } from '@angular/core'
import { MatDialog } from '@angular/material/dialog'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Post } from 'src/app/interfaces/Post'
import { PostService } from 'src/app/services/post.service'
import { PostsDialog } from './posts-dialog.component'

@Component({
  selector: 'app-edit-posts-component',
  templateUrl: './edit-posts.component.html',
  styleUrls: ['./edit-posts.component.css'],
})
export class EditPostsComponent implements OnInit {
  posts: Post[] = []

  emptyPost: Post = {
    titulo: '',
    descripcion: '',
    fotos: [{ id: 0, image: '' }],
  }
  loading = true

  index = 0
  constructor(
    private matSnackBar: MatSnackBar,
    private postService: PostService,
    public dialog: MatDialog
  ) {
    this.emptyPost.fotos.pop()
  }

  ngOnInit(): void {
    this.postService.getPosts().subscribe((res) => {
      this.loading = false
      if (res.data.posts) this.posts = res.data.posts
    })
  }

  deletePost(id: number | undefined) {
    if (!id) return
    this.postService.deletePost(id).subscribe((res) => {
      this.posts = this.posts.filter((post) => post.id !== id)
    })
  }

  openDialog(post: Post): void {
    const dialogRef = this.dialog.open(PostsDialog, {
      width: '90%',
      maxWidth: 800,
      data: { ...post },
    })

    dialogRef.afterClosed().subscribe((result: Post) => {
      if (result) {
        this.postService.updatePost(result).subscribe(
          (res) => {
            if (res.data.post)
              this.posts[this.posts.indexOf(post)] = res.data.post
            this.matSnackBar.open('Se guardaron los cambios', void 0, { duration: 3000 })
          },
          (err) => {
            this.matSnackBar.open('Ocurrio un error', void 0, { duration: 3000 })
            this.ngOnInit()
          }
        )
      } else {
        this.ngOnInit()
      }
    })
  }
}
