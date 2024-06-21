import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { env } from '../../../../config/enviroments';
import axios from 'axios';
import { CookieService } from 'ngx-cookie-service';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import { capitalize } from '../../core/util/capitalize.util';

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
  name: string = '';
  rg: string = '';
  stateRg: string = '';

  constructor(private cookieService: CookieService) {}

  ngAfterViewInit() {
    this.setIconLocation('password', 'visibility_icon');
  }

  backHistory() {
    window.history.back();
  }

  async handleSubmit() {
    const params = {
      name: this.name,
      cellphone: this.cellphone,
      email: this.email,
      cpf: this.cpf,
      rg: this.rg,
      stateRg: this.stateRg,
      birthDate: this.bday,
      password: this.password,
    };
    try {
      await axios.post(env.apiUrl + '/user/v1/set', params);
      alert('Usuário cadastrado com sucesso!');
      location.href = '/home';
    } catch (err: any) {
      const message = err.response.data.message;
      console.warn(message);

      if (message == 'Error: User must be over 18 years old.') {
        alert('O usuário precisa ser maior de 18 anos.');
        return;
      }
      if (
        message ==
        'Error: The system was unable to register the user. Probably user already registered.'
      ) {
        alert('Usuário já cadastrado');
        return;
      }
      if (message == 'Error: The entered cpf field is invalid.') {
        alert('CPF inválido');
        return;
      }
      alert('Erro interno.');
    }
  }

  setRg(event: any) {
    this.rg = event.target.value;
  }

  setStateRg(event: any) {
    this.stateRg = event.target.value.toUpperCase();
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
      !this.bday
    ) {
      alert('Não podem ter campos vazios.');
      return false;
    }
    if (this.password !== this.secondPassword) {
      alert('As senhas não são iguais');
      return false;
    }
    this.handleSubmit();
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

      icon.style.top = inputTop + 'px' + '5cm';
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
