import { Component, AfterViewInit, NgModule } from '@angular/core';
import axios from 'axios';
import { env } from '../../../config/enviroments';
import { CookieService } from 'ngx-cookie-service';
import { LoaderComponent } from '../loader/loader.component';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [LoaderComponent, CommonModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements AfterViewInit {
  password: string = "";
  seePassword: boolean = false;
  email: String = "";
  loading: boolean = false;

  constructor(private cookieService: CookieService) { }

  ngAfterViewInit() {
    window.addEventListener("keydown", (e) => {
      if (e.key === "Enter") {
        this.loginEvent();
      }
    })
    this.setIconLocation("password", "visibility_icon");
  }

  saveCookie(cookieName: string, cookieValue: string) {
    const expirationDate = new Date();
    expirationDate.setDate(expirationDate.getDate() + 7);
    
    this.cookieService.set(cookieName, cookieValue, expirationDate);
  }

  setPassword(event: any) {
    this.password = event.target.value;
  }

  setIconLocation(inputId: string, iconId: string) {
    const input = document.getElementById(inputId);
    const icon = document.getElementById(iconId);
    
    if (input && icon) {
      const inputRect = input.getBoundingClientRect();
      const inputTop = inputRect.top;
      const inputRight = inputRect.right;
      
      icon.style.top = inputTop + 'px';
      icon.style.right = (window.innerWidth - inputRight) + 'px';
    }
  }

  toggleSeePassword() {
    const icon = document.getElementById("visibility_icon");
    this.seePassword = !this.seePassword;
    if (icon) {
      icon.textContent = this.seePassword ? "visibility" : "visibility_off";
    }
  }
  
  setEmail(event: any) {
    this.email = event.target.value.toLowerCase();
  }

  async loginEvent() {
    if (!this.email && !this.password) {
      return;
    }
    this.loading = true;
    try {
      const params = {
        email: this.email,
        password: this.password
      }
      const response = await axios.post(env.apiUrl + "/user/v1/login", params);
      this.saveCookie("basict:user-token", response.data.token);
      localStorage.setItem("user-name",  response.data.name);
      location.href = "/home";
    } catch (error) {
      console.error("Erro ao efetuar login:", error);
    } finally {
      this.loading = false;
    }
  }
}
