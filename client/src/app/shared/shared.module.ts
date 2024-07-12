import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from './components/button/button.component';
import {
  Eye,
  EyeOff,
  HandCoins,
  LogOut,
  LucideAngularModule,
  Mail,
} from 'lucide-angular';
import { InputComponent } from './components/input/input.component';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [ButtonComponent, InputComponent],
  imports: [
    CommonModule,
    LucideAngularModule.pick({ HandCoins, LogOut, Mail, Eye, EyeOff }),
    ReactiveFormsModule,
  ],
  exports: [ButtonComponent, InputComponent],
})
export class SharedModule {}
