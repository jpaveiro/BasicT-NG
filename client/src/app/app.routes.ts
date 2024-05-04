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

export const routes: Routes = [
  { 
    path: '', 
    component: LoginComponent, 
    title: 'Login | BasicT' 
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'Início | BasicT',
    canActivate: [AuthGuard],
  },
  {
    path: 'sell',
    component: NewSaleComponent,
    title: 'Caixa | BasicT',
    canActivate: [AuthGuard],
  },
  { 
    path: 'register', 
    component: RegisterComponent,
    title: 'Cadastro de usuário | BasicT'
  },
  {
    path: 'product',
    component: ProductComponent,
    title: 'Visualizar produtos | BasicT',
    canActivate: [AuthGuard],
  },
  {
    path: 'product/new',
    component: NewProductComponent,
    title: 'Cadastrar produto | BasicT',
    canActivate: [AuthGuard, AdminGuard],
  },
  {
    path: '**',
    component: NotFoundComponent,
    title: '404 Não encontrado | BasicT'
  },
];
