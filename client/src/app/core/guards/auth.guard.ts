import { Injectable } from '@angular/core';
import { CanActivate, Router } from '@angular/router';
import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root',
})
export class AuthGuard implements CanActivate {
  constructor(
    private readonly authService: AuthService,
    private readonly router: Router
  ) {}

  canActivate(): boolean {
    const user = this.authService.getUser();

    const id = user.token;
    const name = user.name;

    if (!id || !name) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}
