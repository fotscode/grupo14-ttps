import { Component } from '@angular/core'
import { MatSnackBar } from '@angular/material/snack-bar'
import { Categoria } from 'src/app/interfaces/Categoria'
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
    this.categoriesService.saveCategories(category).subscribe((res: any) => {
      this.getCategories()
      this.snackBar.open(res.message, void 0, { duration: 3000 })
    })
  }

  addEmptyCategory() {
    this.categories.push({ nombre: '', color: '' })
  }

  deleteCategory(id: number | undefined) {
    if (id) {
      this.categoriesService.deleteCategory(id).subscribe((res: any) => {
        this.snackBar.open(res.message, void 0, { duration: 3000 })
        this.getCategories()
      })
    }
  }
}
