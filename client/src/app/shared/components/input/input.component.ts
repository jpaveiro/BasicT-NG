import { Component, Input } from '@angular/core';
import { FormControl } from '@angular/forms';

@Component({
  selector: 'custom-input',
  templateUrl: './input.component.html',
  styleUrl: './input.component.scss',
})
export class InputComponent {
  @Input()
  icon: string = '';

  @Input()
  type: 'text' | 'password' | 'date' = 'text';

  @Input({ required: true })
  placeholder!: string;

  @Input()
  control!: FormControl;
}
