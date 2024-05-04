import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root',
})
export class AdminGuard implements CanActivate {
  constructor(private cookieService: CookieService, private router: Router) {}

  canActivate(): boolean {
    const superToken = this.cookieService.get('basict:super-user-token');

    if (superToken) {
      return true;
    }

    alert('Você não tem permissão para acessar essa página');
    this.router.navigate(['/home']);
    return false;
  }
}