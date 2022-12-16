import { Component } from '@angular/core'
import { MatDialog, MatDialogConfig } from '@angular/material/dialog'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Categoria } from 'src/app/interfaces/Categoria'
import { CategoriasResponse } from 'src/app/interfaces/responses/CategoriasResponse'
import { CategoriesService } from 'src/app/services/categories.service'
import { TemplateDialogComponent } from '../template-dialog/template-dialog.component'

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'],
})
export class CategoriesComponent {
  categories: Categoria[] = []

  constructor(
    public popup:MatDialog,
    private categoriesService: CategoriesService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.getCategories()
  }

  getCategories() {
    this.categoriesService.getCategories().subscribe((res) => {
      if(res.data.categorias)
        this.categories = res.data.categorias
    })
  }

  saveCategory(category: Categoria) {
    this.categoriesService
      .saveCategories(category)
      .subscribe((res: CategoriasResponse) => {
        this.categories = this.categories.map((c) => {
          if (c == category && res.data.categoria) {
            return res.data.categoria
          }
          return c
        })
        this.snackBar.open(res.message, void 0, { duration: 3000 })
      })
  }

  addEmptyCategory() {
    this.categories.push({ nombre: '', color: '' })
  }

  deleteCategoryAttempt(category:Categoria){
    const dialogConfig=new MatDialogConfig()
    dialogConfig.disableClose=true
    dialogConfig.autoFocus=false
    dialogConfig.width='50%'
    dialogConfig.data={
      title:'Eliminar Categoria',
      dialog:'eliminar la categoria'
    }
    const reference=this.popup.open(TemplateDialogComponent,dialogConfig)
    reference.afterClosed().subscribe((res)=>{
      if(res){
        this.deleteCategory(category)
      }
    })
  }

  deleteCategory(category: Categoria) {
    if (category.id) {
      this.categoriesService
        .deleteCategory(category.id)
        .subscribe((res) => {
          this.snackBar.open(res.message, void 0, { duration: 3000 })
        })
    }
    this.categories = this.categories.filter((c) => c !== category)
  }

  isNotValid(category: Categoria) {
    return category.nombre.length > 15 || category.nombre=='' || category.color.match(/^#[0-9A-F]{6}$/i) == null
  }
}
