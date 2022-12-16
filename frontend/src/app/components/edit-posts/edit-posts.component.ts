import { Component, OnInit } from '@angular/core'
import { MatDialog, MatDialogConfig } from '@angular/material/dialog'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Post } from 'src/app/interfaces/Post'
import { PostService } from 'src/app/services/post.service'
import { TemplateDialogComponent } from '../template-dialog/template-dialog.component'
import { PostsDialog } from './posts-dialog.component'

@Component({
  selector: 'app-edit-posts-component',
  templateUrl: './edit-posts.component.html',
  styleUrls: ['./edit-posts.component.css'],
})
export class EditPostsComponent implements OnInit {
  posts: Post[] = []
  cantidadPaginas = 0
  paginaActual = 0
  cantidadElementos = 6

  emptyPost: Post = {
    titulo: '',
    descripcion: '',
    fotos: [{ id: 0, image: '' }],
  }
  loading = true

  index = 0
  constructor(
    public popup: MatDialog,
    private matSnackBar: MatSnackBar,
    private postService: PostService,
    public dialog: MatDialog
  ) {
    this.emptyPost.fotos.pop()
  }

  ngOnInit(): void {
    this.getPosts()
  }

  getPosts(page: number = 0) {
    this.loading = true
    this.paginaActual = page
    this.postService.getPosts(page, this.cantidadElementos).subscribe((res) => {
      this.loading = false
      if (res.data.posts) this.posts = res.data.posts
      if (res.data.length)
        this.cantidadPaginas = Math.ceil(
          res.data.length / this.cantidadElementos
        )
    })
  }

  deletePostAttempt(id: number | undefined) {
    const dialogConfig = new MatDialogConfig()
    dialogConfig.disableClose = true
    dialogConfig.autoFocus = false
    dialogConfig.width = '50%'
    dialogConfig.data = {
      title: 'Eliminar Post',
      dialog: 'eliminar el post',
    }
    const reference = this.popup.open(TemplateDialogComponent, dialogConfig)
    reference.afterClosed().subscribe((res) => {
      if (res) {
        this.deletePost(id)
      }
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
            this.posts = this.posts.map((p) => {
              if (p.id === result.id) return result
              return p
            })
            if (!result.id && res.data.post) this.posts.unshift(res.data.post)
            this.matSnackBar.open('Se guardaron los cambios', void 0, {
              duration: 3000,
            })
          },
          (err) => {
            this.matSnackBar.open('Ocurrio un error', void 0, {
              duration: 3000,
            })
            this.ngOnInit()
          }
        )
      } else {
        this.ngOnInit()
      }
    })
  }
}
