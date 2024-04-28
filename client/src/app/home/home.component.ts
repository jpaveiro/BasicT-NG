import { Component } from '@angular/core';
import { CookieService } from 'ngx-cookie-service';
import { env } from '../../../config/enviroments';
import axios from 'axios';
import { CommonModule } from '@angular/common';
import { LoaderComponent } from '../loader/loader.component';

interface Response {
  userName: string;
  productName: string;
  quantity: number
  price: number
  purchaseDate: string;
  purchaseCode: string
}
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
  responseData: Response[] = [];
  loader: boolean = false;
  hasSeller: boolean = false;

  constructor(private cookieService: CookieService) {
    this.userName = this.capitalizeName(
      localStorage.getItem('user-name') ?? ''
    );
    this.request();
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
      const userName = this.capitalizeName(nameResponse.data.body.name);
      const productName = this.capitalizeName(productResponse.data.name);
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
      });
    }
    setTimeout(() => this.loader = false, 1000);
    this.hasSeller = true;
    console.log(this.responseData);
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

  capitalizeName(fullName: string) {
    const splitedName = fullName.toLowerCase().split(' ');
    const capitalizedName = splitedName.map((name) => {
      return name.charAt(0).toUpperCase() + name.slice(1);
    });
    return capitalizedName.join(' ');
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
