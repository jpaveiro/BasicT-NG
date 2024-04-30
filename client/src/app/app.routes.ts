import { Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { AuthGuard } from './auth.guard';
import { NewSaleComponent } from './components/new-sale/new-sale.component';
import { NewProductComponent } from './components/new-product/new-product.component';
import { ProductComponent } from './components/product/product.component';

export const routes: Routes = [
  { 
    path: '', 
    component: LoginComponent, 
    title: 'Login' 
  },
  {
    path: 'home',
    component: HomeComponent,
    title: 'Inicio',
    canActivate: [AuthGuard],
  },
  {
    path: 'sell',
    component: NewSaleComponent,
    title: 'Nova Venda',
    canActivate: [AuthGuard],
  },
  { 
    path: 'register', 
    component: RegisterComponent,
    title: 'Registrar'
  },
  {
    path: 'product',
    component: ProductComponent,
    title: 'Produtos',
    canActivate: [AuthGuard],
  },
  {
    path: 'product/new',
    component: NewProductComponent,
    title: 'Novo produto',
    canActivate: [AuthGuard],
  },
];
