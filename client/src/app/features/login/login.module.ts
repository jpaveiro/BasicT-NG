import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login.component';
import { RouterModule } from '@angular/router';
import { SharedModule } from '../../shared/shared.module';
import { CoreModule } from '../../core/core.module';
import { ReactiveFormsModule } from '@angular/forms';

@NgModule({
  declarations: [LoginComponent],
  imports: [
    RouterModule.forRoot([
      {
        path: 'login',
        title: 'basicT | Login',
        component: LoginComponent,
      },
    ]),
    SharedModule,
    CoreModule,
    CommonModule,
    ReactiveFormsModule,
  ],
})
export class LoginModule {}
