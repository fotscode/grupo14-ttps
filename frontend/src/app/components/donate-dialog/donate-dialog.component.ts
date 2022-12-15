import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Manguito } from 'src/app/interfaces/Manguito';

@Component({
  selector: 'app-donate-dialog',
  templateUrl: './donate-dialog.component.html',
  styleUrls: ['./donate-dialog.component.css']
})
export class DonateDialogComponent {
  editar = false
  constructor(
    public dialogRef: MatDialogRef<DonateDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Manguito
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close()
  }

  isNotValid() {
    return this.data.mensaje.length > 255 || this.data.nombrePersona.length > 255 || this.data.cantidad <= 0 || this.data.cantidad > 1000000
  }
}
