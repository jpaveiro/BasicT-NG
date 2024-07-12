import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ButtonComponent } from './components/button/button.component';
import { HandCoins, LucideAngularModule } from 'lucide-angular';

@NgModule({
  declarations: [ButtonComponent],
  imports: [CommonModule, LucideAngularModule.pick({ HandCoins })],
  exports: [ButtonComponent],
})
export class SharedModule {}
