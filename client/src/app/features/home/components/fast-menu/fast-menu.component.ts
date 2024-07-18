import { Component, Input } from '@angular/core';

@Component({
  selector: 'fast-menu',
  templateUrl: './fast-menu.component.html',
  styleUrl: './fast-menu.component.scss',
})
export class FastMenuComponent {
  @Input({ required: true })
  legend!: string;
}
