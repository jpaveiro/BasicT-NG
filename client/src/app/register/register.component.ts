import { Component } from '@angular/core';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  password: string = '';
  seePassword: boolean = false;
  cpf: String = '';
  cellphone: String = '';
  email: String = '';
  bday: String = '';

  ngAfterViewInit() {
    this.setIconLocation('password', 'visibility_icon');
  }

  handleBday(event: any) {
    const inputValue = event.target.value;
    const isValidDate = /^\d{4}-\d{2}-\d{2}$/.test(inputValue);

    if (isValidDate) {
      console.log(inputValue)
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

      icon.style.top = inputTop + 'px';
      icon.style.right = window.innerWidth - inputRight + 'px';
    }
  }

  formatCpf(event: any) {
    let cpf = event.target.value;

    cpf = cpf.replace(/\D/g, '');

    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');

    event.target.value = cpf;

    this.cpf = cpf;
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
