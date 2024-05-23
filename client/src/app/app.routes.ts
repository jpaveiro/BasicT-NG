import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuard } from './guards/auth/auth.guard';
import { NewSaleComponent } from './components/new-sale/new-sale.component';
import { NewProductComponent } from './components/new-product/new-product.component';
import { ProductComponent } from './components/product/product.component';
import { NotFoundComponent } from './components/not-found/not-found.component';
import { AdminGuard } from './guards/admin/admin.guard';
import { AboutComponent } from './components/about/about.component';
import { DailyOverviewComponent } from './components/daily-overview/daily-overview.component';

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
    canActivate: [AuthGuard, AdminGuard]
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
