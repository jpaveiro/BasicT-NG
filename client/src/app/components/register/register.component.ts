import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { env } from '../../../../config/enviroments';
import axios from 'axios';
import { CookieService } from 'ngx-cookie-service';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { capitalize } from '../../util/capitalize.util';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, NgxMaskDirective, NgxMaskPipe],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  password: string = '';
  secondPassword: string = '';
  seePassword: boolean = false;
  cpf: String = '';
  cellphone: String = '';
  email: String = '';
  bday: String = '';
  overlay: boolean = false;
  adminEmail: string = '';
  adminPassword: string = '';
  name: string = "";
  rg: string = "";
  stateRg: string = "";

  constructor(private cookieService: CookieService) { }

  ngAfterViewInit() {
    if (this.isLogged()) {
      location.href = "/home";
    }
    window.addEventListener('keydown', (e: any) => {
      if (e.key === 'Escape') {
        this.exitOverlay();
      }
    });
    this.setIconLocation('password', 'visibility_icon');
  }

  isLogged(): boolean {
    const token = this.cookieService.get("basict:user-token");
    if (token) {
      return true;
    }
    return false;
  }

  async handleSubmit() {
    const params = {
      email: this.adminEmail,
      password: this.adminPassword
    }
    const response = await axios.post(env.apiUrl + "/user/v1/login", params)
    if (!response.data.token) {
      alert('Usuário ou senha inválidos.');
      return;
    }
    if (response.data.name != "ADMIN") {
      alert('Este usuário não tem permissão para isso.')
      return;
    }
    const secondParams = {
      name: this.name,
      cellphone: this.cellphone,
      email: this.email,
      cpf: this.cpf,
      rg: this.rg,
      stateRg: this.stateRg,
      birthDate: this.bday,
      password: this.password
    }

    try {
      await axios.post(env.apiUrl + "/user/v1/set", secondParams)
      alert("Usuário cadastrado com sucesso!");
      location.href = "/";
    } catch (err: any) {
      const message = err.response.data.message;
      console.warn(message);

      if (message.toLowerCase() == "error: the entered birthdate field is invalid.") {
        alert('Data de nascimento inválida');
        return;
      }
      if(message == "Error: The system was unable to register the user. Probably user already registered.") {
        alert('Usuário já cadastrado');
        return;
      }
      alert("Erro interno.")
    }
    
  }

  setRg(event: any) {
    this.rg = event.target.value;
  }

  setStateRg(event: any) {
    this.stateRg = event.target.value.toUpperCase();
  }

  setAdminEmail(event: any) {
    this.adminEmail = event.target.value.toLowerCase();
  }

  setAdminPassword(event: any) {
    this.adminPassword = event.target.value;
  }

  exitOverlay() {
    this.overlay = false;
  }

  setSecondPassword(e: any) {
    this.secondPassword = e.target.value;
  }

  setName(e: any) {
    this.name = capitalize(e.target.value);
  }

  checkInformations(): boolean {
    if (
      !this.password || 
      !this.secondPassword ||
     !this.cpf ||
     !this.cellphone ||
     !this.email ||
     !this.bday) {
      alert("Não podem ter campos vazios.")
      return false;
    }
    if (this.password!== this.secondPassword) {
      alert("As senhas não são iguais");
      return false;
    }
    this.overlay = true;
    return true;
  }

  handleBday(event: any) {
    const inputValue = event.target.value;
    const isValidDate = /^\d{4}-\d{2}-\d{2}$/.test(inputValue);

    if (isValidDate) {
      console.log(inputValue);
      this.bday = inputValue;
    }
  }

  setPassword(event: any) {
    this.password = event.target.value;
  }

  handleEmail(event: any) {
    this.email = event.target.value.toLowerCase();
  }

  setIconLocation(inputId: string, iconId: string) {
    const input = document.getElementById(inputId);
    const icon = document.getElementById(iconId);

    if (input && icon) {
      const inputRect = input.getBoundingClientRect();
      const inputTop = inputRect.top;
      const inputRight = inputRect.right;

      icon.style.top = (inputTop + 'px') + '5cm';
      icon.style.right = window.innerWidth - inputRight + 'px';
    }
  }

  setCpf(event: any) {
    this.cpf = event.target.value;  
  }

  setCellphone(event: any) {
    this.cellphone = event.target.value;
  }

  toggleSeePassword() {
    const icon = document.getElementById('visibility_icon');
    this.seePassword = !this.seePassword;
    if (icon) {
      icon.textContent = this.seePassword ? 'visibility' : 'visibility_off';
    }
  }
}
