import { Routes } from '@angular/router';
import { HomeComponent } from './pages/home/home.component';
import { LoginComponent } from './pages/login/login.component';
import { RegisterComponent } from './pages/register/register.component';
import { AuthGuard } from './core/guards/auth/auth.guard';
import { NewSaleComponent } from './pages/new-sale/new-sale.component';
import { NewProductComponent } from './pages/new-product/new-product.component';
import { ProductComponent } from './pages/product/product.component';
import { NotFoundComponent } from './pages/not-found/not-found.component';
import { AdminGuard } from './core/guards/admin/admin.guard';
import { AboutComponent } from './pages/about/about.component';
import { DailyOverviewComponent } from './pages/daily-overview/daily-overview.component';

const APP_TITLE = '| BasicT';

export const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    title: `Login ${APP_TITLE}`,
  },
  {
    path: 'home',
    component: HomeComponent,
    title: `Início ${APP_TITLE}`,
    canActivate: [AuthGuard],
  },
  {
    path: 'about',
    component: AboutComponent,
    title: `Sobre ${APP_TITLE}`,
    canActivate: [AuthGuard],
  },
  {
    path: 'sell',
    component: NewSaleComponent,
    title: `Caixa ${APP_TITLE}`,
    canActivate: [AuthGuard],
  },
  {
    path: 'daily-overview',
    component: DailyOverviewComponent,
    title: `Visão geral diária ${APP_TITLE}`,
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: 'register',
    component: RegisterComponent,
    title: `Cadastro de usuário ${APP_TITLE}`,
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: 'product',
    component: ProductComponent,
    title: `Visualizar produtos ${APP_TITLE}`,
    canActivate: [AuthGuard],
  },
  {
    path: 'product/new',
    component: NewProductComponent,
    title: `Cadastrar produto ${APP_TITLE}`,
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: '**',
    component: NotFoundComponent,
    title: `404 Não encontrado ${APP_TITLE}`,
  },
];
