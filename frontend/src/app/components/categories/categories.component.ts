import { Component } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Categoria } from 'src/app/interfaces/Categoria'
import { CategoriasResponse } from 'src/app/interfaces/responses/CategoriasResponse'
import { CategoriesService } from 'src/app/services/categories.service'

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css'],
})
export class CategoriesComponent {
  categories: Categoria[] = []

  constructor(
    private categoriesService: CategoriesService,
    private snackBar: MatSnackBar
  ) {}

  ngOnInit(): void {
    this.getCategories()
  }

  getCategories() {
    this.categoriesService.getCategories().subscribe((res: any) => {
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

  deleteCategory(category: Categoria) {
    if (category.id) {
      this.categoriesService
        .deleteCategory(category.id)
        .subscribe((res: any) => {
          this.snackBar.open(res.message, void 0, { duration: 3000 })
        })
    }
    this.categories = this.categories.filter((c) => c !== category)
  }
}
