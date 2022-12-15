import { Component, Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';

@Component({
  selector: 'app-template-dialog',
  templateUrl: './template-dialog.component.html',
  styleUrls: ['./template-dialog.component.css']
})
export class TemplateDialogComponent {
  constructor(@Inject(MAT_DIALOG_DATA) public data:{title:string,dialog:string}){}
}
