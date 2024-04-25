import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { env } from '../../../config/enviroments';
import axios from 'axios';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule],
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

  ngAfterViewInit() {
    window.addEventListener('keydown', (e: any) => {
      if (e.key === 'Escape') {
        this.exitOverlay();
      }
    });
    this.setIconLocation('password', 'visibility_icon');
  }

  async handleSubmit() {
    const params = {
      email: this.adminEmail,
      password: this.adminPassword
    }
    const response = await axios.post(env.apiUrl + "/user/v1/login", params)
    if (!response.data.token) {
      alert('Usuário ou senha inválidos');
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
      location.href = "/";
    } catch (err: any) {
      const message = err.response.data.message;

      if (message.toLowerCase() == "error: the entered birthdate field is invalid.") {
        alert('Data de nascimento inválida');
        return;
      }
      alert("Erro interno.")
    }
    
  }

  formatRg(event: any) {
    const rg = event.target.value.replace(/\D/g, '');
    this.rg = rg.substring(0, 2) + '.' + rg.substring(2, 5) + '.' + rg.substring(5, 8) + '-' + rg.substring(8);
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
    this.name = e.target.value;
  }

  checkInformations() {
    if (
      !this.password || 
      !this.secondPassword ||
     !this.cpf ||
     !this.cellphone ||
     !this.email ||
     !this.bday) {
      alert("Não podem ter campos vazios.")
      return;
    }
    if (this.password!== this.secondPassword) {
      alert("As senhas não são iguais");
      return;
    }
    this.overlay = true;
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

  formatCpf(event: any) {
    let cpf = event.target.value.replace(/\D/g, '');
    this.cpf = cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9, 11);
  }

  formatCellphone(event: any) {
    let phone = event.target.value;

    phone = phone.replace(/\D/g, '');

    phone = phone.replace(/(\d{2})(\d)/, '($1) $2');
    phone = phone.replace(/(\d{5})(\d)/, '$1-$2');
    event.target.value = phone;
    this.cellphone = phone;
  }

  toggleSeePassword() {
    const icon = document.getElementById('visibility_icon');
    this.seePassword = !this.seePassword;
    if (icon) {
      icon.textContent = this.seePassword ? 'visibility' : 'visibility_off';
    }
  }
}
