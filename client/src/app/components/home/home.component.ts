import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { env } from '../../../../config/enviroments';
import axios from 'axios';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from '../loader/loader.component';
import { HomeTableResponse } from '../../interfaces/homeTableResponse.interface';
import { capitalize } from '../../util/capitalize.util';
import { AdminGuard } from '../../guards/admin/admin.guard';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [CommonModule, LoaderComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
})
export class HomeComponent {
  userName: string = '';
  page: number = 1;
  responseData: HomeTableResponse[] = [];
  loader: boolean = false;
  hasSeller: boolean = false;
  isAdmin: boolean = false;

  constructor(private cookieService: CookieService) {
    this.userName = capitalize(
      localStorage.getItem('user-name') ?? ''
    );
    this.isAdmin = this.isAdminLogged();
    this.request();
  }

  isAdminLogged(): boolean {
    const superToken = this.cookieService.get('basict:super-user-token');

    if (superToken) {
      return true;
    }

    return false;
  }

  async request() {
    this.loader = true;
    this.responseData = [];
    let response;
    try {
      response = await axios.get(
        env.apiUrl + '/purchase/v1/get?page=' + this.page
      );
    } catch {
      this.loader = false;
      return;
    }

    console.log(response.data)
    for (const product of response.data.content) {
      const params = {
        idProduct: product.idProduct,
      };
      const productResponse = await axios.post(
        env.apiUrl + '/product/v1/get',
        params
      );
      const nameResponse = await axios.get(
        env.apiUrl + '/user/v1/get?id=' + product.idUser
      );
      const userName = capitalize(nameResponse.data.name);
      const productName = capitalize(productResponse.data.name);
      const purchaseDate = new Date(
        product.purchaseDate
      ).toLocaleDateString('br');
  
      this.responseData.push({
        userName: userName,
        productName: productName,
        quantity: product.productQuantity,
        price: product.totalAmount,
        purchaseDate: purchaseDate,
        purchaseCode: product.purchaseCode,
        paymentMethod: capitalize(product.paymentMethod),
      });
    }
    this.loader = false;
    this.hasSeller = true;
  }

  advancePage(advance: boolean) {
    if (advance) {
      this.page++;
      this.request();
      return
    }
    if (this.page > 1 && !advance) {
      this.page--;
      this.request();
    }

  }

  logout() {
    localStorage.removeItem('user-name');
    this.cookieService.deleteAll();
    location.href = '/';
  }

  redirect(route: any) {
    location.href = '/' + route;
  }
}
