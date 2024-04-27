import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';

interface FinalData {
  name: string;
  quantity: number;
  price: number;
}

@Component({
  selector: 'app-new-sale',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './new-sale.component.html',
  styleUrl: './new-sale.component.scss'
})
export class NewSaleComponent {
  name: string = "";
  quantity: number = 1;
  price: number = 0;
  finalPrice: number = 0;
  seeTable: boolean = false;

  allData: FinalData[] = [];

  setName(e: any) {
    this.name = e.target.value.toUpperCase();
  }

  setQuantity(e: any) {
    if (parseInt(e.target.value) <= 0) {
      return;
    }
    this.quantity = parseInt(e.target.value);
  }

  setPrice(e: any) {
    if (parseFloat(e.target.value) <= 0) {
      return;
    }
    this.price = parseFloat(e.target.value);
  }

  addProducts() {
    if (
      this.name &&
      this.quantity &&
      this.price
    ) {
      this.seeTable = true;
      this.allData.push({name: this.name, quantity: this.quantity, price: this.price})
      for (let product of this.allData) {
        this.finalPrice += product.price * ((product.quantity >= 1) ? product.quantity : 1);
      }
      return;
    }
    alert("Preencha todos os campos");
  }

  redirect(route: any) {
    location.href = "/" + route;
  }
}
