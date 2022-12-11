import { Component, Inject } from '@angular/core'
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { Post } from 'src/app/interfaces/Post'

@Component({
  selector: 'posts-dialog',
  templateUrl: 'posts-dialog.html',
})
export class PostsDialog {
  editar = false
  constructor(
    public dialogRef: MatDialogRef<PostsDialog>,
    @Inject(MAT_DIALOG_DATA) public data: Post
  ) {
    this.editar = data.titulo != ''
  }

  onNoClick(): void {
    this.dialogRef.close()
  }
  isEditar() {
    return this.editar
  }

  onFileSelected(index:number) {
    if (index==-1){
      this.data.fotos.push({ image: '' })
      index=this.data.fotos.length-1
    }
    const inputNode: any = document.querySelector('#compImg-'+index)
    if (typeof FileReader !== 'undefined') {
      const reader = new FileReader()

      reader.onload = (e: any) => {
        this.data.fotos[index].image = this._arrayBufferToBase64(e.target.result)
      }
      reader.readAsArrayBuffer(inputNode.files[0])
    }
  }

  _arrayBufferToBase64(buffer: ArrayBuffer) {
    var binary = ''
    var bytes = new Uint8Array(buffer)
    var len = bytes.byteLength
    for (var i = 0; i < len; i++) {
      binary += String.fromCharCode(bytes[i])
    }
    return window.btoa(binary)
  }
}
