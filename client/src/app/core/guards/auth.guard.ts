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
    const id = this.authService.id();
    const email = this.authService.name();

    if (!id || !email) {
      this.router.navigate(['/login']);
      return false;
    }

    return true;
  }
}
