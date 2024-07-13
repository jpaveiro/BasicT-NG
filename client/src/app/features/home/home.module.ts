import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';

@NgModule({
  declarations: [HomeComponent],
  imports: [
    CommonModule,
    RouterModule.forRoot([
      {
        path: 'home',
        title: 'basicT | Inicio',
        component: HomeComponent,
        canActivate: [AuthGuard],
      },
    ]),
  ],
})
export class HomeModule {}
