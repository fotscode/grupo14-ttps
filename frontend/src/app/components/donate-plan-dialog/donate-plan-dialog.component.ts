import { Component, Inject } from '@angular/core'
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { Pago } from 'src/app/interfaces/Pago'

@Component({
  selector: 'app-donate-plan-dialog',
  templateUrl: './donate-plan-dialog.component.html',
  styleUrls: ['../donate-dialog/donate-dialog.component.css'],
})
export class DonatePlanDialogComponent {
  editar = false
  constructor(
    public dialogRef: MatDialogRef<DonatePlanDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Pago
  ) {}

  onNoClick(): void {
    this.dialogRef.close()
  }

  isNotValid() {
    return (
      this.data.mensaje.length > 255 ||
      this.data.nombrePersona.length > 255 ||
      this.data.contacto.length > 255 ||
      this.data.contacto.length < 10 
    )
  }
}
