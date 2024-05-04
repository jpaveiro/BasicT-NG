import { Injectable } from "@angular/core";
import { CanActivate, Router } from "@angular/router";
import { CookieService } from "ngx-cookie-service";


@Injectable({
  providedIn: "root"
})
export class AuthGuard implements CanActivate {
  constructor(private cookieService: CookieService, private router: Router) {}

  canActivate(): boolean {
    const token = this.cookieService.get("basict:user-token");
    if (token) {
      return true;
    } else {
      
      alert("Você não tem permissão para acessar essa página");
      this.router.navigate(["/"]);
      return false;
    }
  }
}