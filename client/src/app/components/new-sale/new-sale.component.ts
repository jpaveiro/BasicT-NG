import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { LoaderComponent } from '../loader/loader.component';
import axios from 'axios';
import { env } from '../../../../config/enviroments';
import { CookieService } from 'ngx-cookie-service';
import { ProductsToSellTableRequest } from '../../interfaces/productsToSellTableRequest.interface';
import { capitalize } from '../../util/capitalize.util';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-sale',
  standalone: true,
  imports: [CommonModule, LoaderComponent, FormsModule],
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
  productIds: string[] = [];
  selectedPaymentMethod: string = "dinheiro";

  allData: ProductsToSellTableRequest[] = [];

  constructor(private cookieService: CookieService) {
    this.purchaseCode = this.generatePurchaseCode();
    this.userId = this.cookieService.get("basict:user-id");
  }

  setSelectedPaymentMethod(event: any) {
    console.log(this.selectedPaymentMethod);
  }

  generatePurchaseCode(): string {
    return Math.random().toString(36).substring(2, 15);
  }

  setCodeBar(e: any) {
    this.codeBar = e.target.value.toUpperCase();
  }
  
  async sell() {
    this.loader = true;
    for (let i = 0; i < this.allData.length; i++) {
      const product = this.allData[i];
      const productId = this.productIds[i];
      try {
        const params = {
          purchaseCode: this.purchaseCode,
          userId: this.userId,
          quantity: product.quantity,
          productId: productId,
          totalAmount: product.price,
          paymentMethod: this.selectedPaymentMethod.toUpperCase(),
        };
        await axios.post(env.apiUrl + "/purchase/v1/sell", params);
        alert("Você vendeu!");
        location.href = "/home";
      } catch (error) {
        alert("Não foi possível vender");
        break;
      } finally {
        this.loader = false;
      }
    }
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
    let productId: string = "";

    if (
      this.codeBar &&
      this.quantity 
    ) {
      this.loader = true;
      try {
        const response = await axios.post(env.apiUrl + "/product/v1/get", {idProduct: this.codeBar});
        productName = response.data.name;
        price = response.data.price * this.quantity;
        productId = response.data.idProduct;
      } catch {
        alert("Produto não cadastrado.")
        this.loader = false;
        return;
      }
      this.seeTable = true;
      this.allData.push({name: capitalize(productName), quantity: this.quantity, price: price.toFixed(2)})
      this.productIds.push(productId);
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
