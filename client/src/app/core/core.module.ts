import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HeaderComponent } from './components/header/header.component';
import { Loader, LucideAngularModule, Menu } from 'lucide-angular';
import { AppRoutingModule } from '../app-routing.module';
import { SharedModule } from '../shared/shared.module';
import { FooterComponent } from './components/footer/footer.component';
import { LoaderComponent } from './components/loader/loader.component';

@NgModule({
  declarations: [HeaderComponent, FooterComponent, LoaderComponent],
  imports: [
    CommonModule,
    LucideAngularModule.pick({ Menu, Loader }),
    AppRoutingModule,
    SharedModule,
  ],
  exports: [HeaderComponent, FooterComponent, LoaderComponent],
})
export class CoreModule {}
