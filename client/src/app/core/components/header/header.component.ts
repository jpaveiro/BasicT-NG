import { Component, Input, signal } from '@angular/core';
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

  showMenu = signal(false);

  constructor(private readonly authService: AuthService) {}

  changeMenuVisible() {
    this.showMenu.update((value) => !value);
  }

  logout() {
    this.authService.logout();
  }
}
