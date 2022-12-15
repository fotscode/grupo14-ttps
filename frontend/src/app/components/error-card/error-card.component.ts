import { Component, Input } from '@angular/core'

@Component({
  selector: 'app-error-card',
  templateUrl: './error-card.component.html',
  styleUrls: ['./error-card.component.css'],
})
export class ErrorCardComponent {
  @Input() title: string
  @Input() message: string
  @Input() condition: boolean
  @Input() action: string
  @Input() link:string

  constructor() {
    this.title = ''
    this.message = ''
    this.condition = false
    this.action = ''
    this.link = ''
  }
}
