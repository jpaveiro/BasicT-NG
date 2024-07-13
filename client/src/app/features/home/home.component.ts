import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  name: string;

  constructor(private readonly authService: AuthService) {
    const user = authService.getUser();

    this.name = user.name ?? '';
  }
}
