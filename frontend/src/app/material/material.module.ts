import { NgModule } from '@angular/core'
import { MatSnackBarModule } from '@angular/material/snack-bar'
import { MatDialogModule } from '@angular/material/dialog'
import { MatFormFieldModule } from '@angular/material/form-field'
import { MatInputModule } from '@angular/material/input'
import { MatButtonModule } from '@angular/material/button'

const MaterialComponents = [
  MatDialogModule,
  MatSnackBarModule,
  MatFormFieldModule,
  MatInputModule,
  MatButtonModule,
]

@NgModule({
  imports: [MaterialComponents],
  exports: [MaterialComponents],
})
export class MaterialModule {}
