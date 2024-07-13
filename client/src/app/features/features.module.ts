import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginModule } from './login/login.module';
import { HomeModule } from './home/home.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, LoginModule, HomeModule],
  exports: [LoginModule],
})
export class FeaturesModule {}
