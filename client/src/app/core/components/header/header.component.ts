import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {
  @Input({ required: true })
  isLogged!: boolean;

  constructor(private readonly authService: AuthService) {}

  logout() {
    this.authService.logout();
  }
}
