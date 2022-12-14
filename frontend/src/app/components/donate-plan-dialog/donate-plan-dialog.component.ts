import { Component, Inject } from '@angular/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Pago } from 'src/app/interfaces/Pago';

@Component({
  selector: 'app-donate-plan-dialog',
  templateUrl: './donate-plan-dialog.component.html',
})
export class DonatePlanDialogComponent {
  editar = false
  constructor(
    public dialogRef: MatDialogRef<DonatePlanDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: Pago
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close()
  }

}