import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from './components/button/button.component';
import { HandCoins, LogOut, LucideAngularModule } from 'lucide-angular';

@NgModule({
  declarations: [ButtonComponent],
  imports: [CommonModule, LucideAngularModule.pick({ HandCoins, LogOut })],
  exports: [ButtonComponent],
})
export class SharedModule {}
