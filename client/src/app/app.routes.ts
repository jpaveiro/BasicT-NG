import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { AuthGuard } from './auth.guard';

export const routes: Routes = [
  {path: "", component: LoginComponent, title: "Login"},
  {path: "home", component: HomeComponent, title: "Inicio", canActivate: [AuthGuard]},
  {path: "register", component: RegisterComponent, title: "Registrar"},
];
