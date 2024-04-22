import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';

export const routes: Routes = [
  {path: "", component: LoginComponent, title: "Login"},
  {path: "home", component: HomeComponent, title: "Home"},
];
