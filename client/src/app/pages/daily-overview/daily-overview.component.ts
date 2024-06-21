import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { capitalize } from '../../core/util/capitalize.util';
import { LoaderComponent } from '../loader/loader.component';
import { CommonModule } from '@angular/common';
import { setTimeout } from 'timers/promises';

@Component({
  selector: 'app-daily-overview',
  standalone: true,
  imports: [LoaderComponent, CommonModule],
  templateUrl: './daily-overview.component.html',
  styleUrl: './daily-overview.component.scss',
})
export class DailyOverviewComponent {
  userName: string = '';
  today_sales = 0;
  loader: boolean = false;

  constructor(private cookieService: CookieService) {
    this.userName = capitalize(localStorage.getItem('user-name') ?? '');
    this.request();
  }

  isAdminLogged(): boolean {
    const superToken = this.cookieService.get('basict:super-user-token');

    if (superToken) {
      return true;
    }

    return false;
  }

  goBack() {
    window.history.back();
  }

  async request() {
    this.loader = true;
    this.loader = false;
  }
}
