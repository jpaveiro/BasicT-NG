import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { LucideAngularModule, Menu } from 'lucide-angular';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';
import { FooterComponent } from './components/footer/footer.component';

@NgModule({
  declarations: [HeaderComponent, FooterComponent],
  imports: [
    CommonModule,
    LucideAngularModule.pick({ Menu }),
    AppRoutingModule,
    SharedModule,
  ],
  exports: [HeaderComponent, FooterComponent],
})
export class CoreModule {}
