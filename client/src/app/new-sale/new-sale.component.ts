import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LoaderComponent } from '../loader/loader.component';
import axios from 'axios';
import { env } from '../../../config/enviroments';
import { CookieService } from 'ngx-cookie-service';

interface FinalData {
  name: string;
  quantity: number;
  price: string;
}

@Component({
  selector: 'app-new-sale',
  standalone: true,
  imports: [CommonModule, LoaderComponent],
  templateUrl: './new-sale.component.html',
  styleUrl: './new-sale.component.scss'
})
export class NewSaleComponent {
  codeBar: string = "";
  quantity: number = 1;
  finalPrice: number = 0;
  seeTable: boolean = false;
  loader: boolean = false;
  purchaseCode: string = "";
  userId: string = "";

  allData: FinalData[] = [];

  constructor(private cookieService: CookieService) {
    this.purchaseCode = this.generatePurchaseCode();
    this.userId = this.cookieService.get("basict:user-id");
  }

  generatePurchaseCode(): string {
    return Math.random().toString(36).substring(2, 15);
  }

  setCodeBar(e: any) {
    this.codeBar = e.target.value.toUpperCase();
  }

  setQuantity(e: any) {
    if (parseInt(e.target.value) <= 0) {
      return;
    }
    this.quantity = parseInt(e.target.value);
  }

  async addProducts() {
    let productName: any = null;
    let price: number = 0;

    if (
      this.codeBar &&
      this.quantity 
    ) {
      this.loader = true;
      try {
        const response = await axios.post(env.apiUrl + "/product/v1/get", {idProduct: this.codeBar});
        productName = response.data.name;
        price = response.data.price * this.quantity;
      } catch {
        alert("Produto não cadastrado.")
        this.loader = false;
        return;
      }
      this.seeTable = true;
      this.allData.push({name: productName, quantity: this.quantity, price: price.toFixed(2)})
      this.finalPrice = 0;
      for (let product of this.allData) {
        this.finalPrice += parseFloat(product.price);
      }
      this.loader = false;
      return;
    }
    alert("Preencha todos os campos");
  }

  redirect(route: any) {
    location.href = "/" + route;
  }
}
