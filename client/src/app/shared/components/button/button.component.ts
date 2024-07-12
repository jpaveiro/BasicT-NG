import { Component, Input } from '@angular/core';

@Component({
  selector: 'custom-button',
  templateUrl: './button.component.html',
  styleUrl: './button.component.scss',
})
export class ButtonComponent {
  @Input({ required: true })
  child!: string;

  @Input()
  variant: 'green' | 'no-color' | 'red' = 'no-color';

  @Input()
  icon: string = '';
}
