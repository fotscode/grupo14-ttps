import { Component, EventEmitter, Input, Output } from '@angular/core';

@Component({
  selector: 'app-paginator',
  templateUrl: './paginator.component.html',
  styleUrls: ['./paginator.component.css']
})
export class PaginatorComponent {
  @Input() cantidadPaginas: number = 0;
  @Input() paginaActual: number = 0;
  @Output() paginaSeleccionada = new EventEmitter<number>();

  counter(i: number) {
    return new Array(i);
  }
}
