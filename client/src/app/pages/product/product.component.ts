import { Component } from '@angular/core';
import { redirect } from '../../util/redirect.util';
import { LoaderComponent } from '../loader/loader.component';
import { CommonModule } from '@angular/common';
import axios from 'axios';
import { env } from '../../../../config/enviroments';
import { ProductTableResponse } from '../../core/models/productTableResponse.interface';
import { capitalize } from '../../util/capitalize.util';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [LoaderComponent, CommonModule],
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss',
})
export class ProductComponent {
  loader: boolean = false;
  seeTable: boolean = false;
  page: number = 1;

  products: ProductTableResponse[] = [];

  constructor() {
    this.getProducts();
  }

  goBack() {
    window.history.back();
  }

  redirect(where: string) {
    redirect(where);
  }

  async getProducts() {
    this.loader = true;
    try {
      const response = await axios.get(
        env.apiUrl + '/product/v1/getAll?page=' + this.page
      );
      const data = response.data.content;

      for (let product of data) {
        this.products.push({
          idProduct: product.idProduct,
          name: capitalize(product.name),
          price: product.price,
        });
      }

      this.seeTable = true;
      this.loader = false;
    } catch {
      this.loader = false;
    }
  }
}
