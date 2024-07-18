import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HomeComponent } from './home.component';
import { RouterModule } from '@angular/router';
import { AuthGuard } from '../../core/guards/auth.guard';
import { CoreModule } from '../../core/core.module';
import { SharedModule } from '../../shared/shared.module';
import { FastMenuComponent } from './components/fast-menu/fast-menu.component';

@NgModule({
  declarations: [HomeComponent, FastMenuComponent],
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
    SharedModule,
    CoreModule,
  ],
})
export class HomeModule {}
