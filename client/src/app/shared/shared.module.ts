import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from './components/button/button.component';
import { HandCoins, LogOut, LucideAngularModule } from 'lucide-angular';
import { InputComponent } from './components/input/input.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [ButtonComponent, InputComponent],
  imports: [
    CommonModule,
    LucideAngularModule.pick({ HandCoins, LogOut }),
    ReactiveFormsModule,
  ],
  exports: [ButtonComponent, InputComponent],
})
export class SharedModule {}
