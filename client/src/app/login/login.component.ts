import { Component, AfterViewInit } from '@angular/core';
import axios from 'axios';
import { env } from '../../../config/enviroments';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent implements AfterViewInit {
  password: string = "";
  seePassword: boolean = false;
  email: String = "";

  ngAfterViewInit() {
    this.posicionarIcone("password", "visibility_icon");
  }

  setPassword(event: any) {
    this.password = event.target.value;
  }

  posicionarIcone(inputId: string, iconId: string) {
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
    try {
      const params = {
        email: this.email,
        password: this.password
      }
      const response = await axios.post(env.apiUrl + "/user/v1/login", params);
      console.log(response.data);
    } catch (error) {
      console.error("Erro ao efetuar login:", error);
    }
  }
}
