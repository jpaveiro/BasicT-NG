import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(private cookieService: CookieService, private router: Router) {}

  canActivate(): boolean {
    let tokens = [];

    for (let i = 0; i <= 9; i++) {
      const token = this.cookieService.get(`basict:user-token${i}`);
      if (token) {
        tokens.push(true);
      }
    }

    if (!tokens[0]) {
      alert('Você não tem permissão para acessar essa página');
      this.router.navigate(['/']);
      return false;
    }

    for (let i = 0; i <= tokens.length; i++) {
      if (tokens[i] == false) {
        alert('Você não tem permissão para acessar essa página');
        this.router.navigate(['/']);

        return false;
      }
    }

    return true;
  }
}
