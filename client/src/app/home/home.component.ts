import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent {
  userName: string = "";

  constructor(private cookieService: CookieService) {
    this.userName = this.capitalizeName(localStorage.getItem("user-name") ?? "");
  }

  capitalizeName(fullName: string) {
    const splitedName = fullName.toLowerCase().split(" ");
    const capitalizedName = splitedName.map((name) => {
      return name.charAt(0).toUpperCase() + name.slice(1);
    });
    return capitalizedName.join(" ");
  }

  logout() {
    localStorage.removeItem("user-name");
    this.cookieService.deleteAll();
    location.href = "/";
  }

  redirect(route: any) {
    location.href = "/" + route;
  }
}
