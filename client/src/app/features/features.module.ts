import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginModule } from './login/login.module';

@NgModule({
  declarations: [],
  imports: [CommonModule, LoginModule],
  exports: [LoginModule],
})
export class FeaturesModule {}
