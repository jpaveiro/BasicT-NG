import { Component } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss',
})
export class LoginComponent {
  userForm = new FormGroup({
    email: new FormControl('', [Validators.required, Validators.email]),
    password: new FormControl('', [
      Validators.required,
      Validators.minLength(8),
    ]),
  });

  constructor(private readonly toastr: ToastrService) {}

  onSubmit(): void {
    if (this.userForm.invalid) {
      if (this.userForm.get('email')?.hasError('email')) {
        this.toastr.error('Insira um email válido.', 'Erro!');
        return;
      }

      if (this.userForm.get('password')?.hasError('minlength')) {
        this.toastr.error(
          'A senha precisa ter no minimo 8 caracteres.',
          'Erro!'
        );
        return;
      }

      this.toastr.error('Todos os dados são obrigatórios.', 'Atenção');
      return;
    }
  }
}
