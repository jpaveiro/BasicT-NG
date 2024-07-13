import { HttpClient } from '@angular/common/http';
import { Injectable, signal } from '@angular/core';
import { DEVENV } from '../../config/env.dev';
import { ToastrService } from 'ngx-toastr';
import { catchError, throwError } from 'rxjs';
import { Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';
@Injectable({
  providedIn: 'root',
})
export class AuthService {
  constructor(
    private readonly httpClient: HttpClient,
    private readonly toastr: ToastrService,
    private readonly router: Router,
    private readonly cookieService: CookieService
  ) {}

  login(email: string, password: string) {
    this.httpClient
      .post(`${DEVENV.API_URL}api/user/v1/login`, {
        email,
        password,
      })
      .pipe(
        catchError((error) => {
          if (error.status == 401) {
            this.toastr.error(
              'Informações incorretas ou usuário inexistente.',
              'Erro!'
            );
          }

          return throwError(error);
        })
      )
      .subscribe((response: any) => {
        this.cookieService.set('basict:token', response.userId);
        this.cookieService.set('basict:name', response.name);

        this.router.navigate(['/home']);
      });
  }

  logout() {
    this.cookieService.deleteAll();

    this.router.navigate(['/login']);
  }

  getUser() {
    return {
      token: this.cookieService.get('basict:token'),
      name: this.cookieService.get('basict:name'),
    };
  }
}
