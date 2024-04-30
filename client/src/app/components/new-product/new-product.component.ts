import { Component } from '@angular/core';
import { redirect } from '../../util/redirect.util';
import { LoaderComponent } from '../loader/loader.component';
import { CommonModule } from '@angular/common';
import { capitalize } from '../../util/capitalize.util';
import { NgxMaskDirective, NgxMaskPipe } from 'ngx-mask';
import axios from 'axios';
import { env } from '../../../../config/enviroments';

@Component({
  selector: 'app-new-product',
  standalone: true,
  imports: [LoaderComponent, CommonModule, NgxMaskDirective, NgxMaskPipe],
  templateUrl: './new-product.component.html',
  styleUrl: './new-product.component.scss'
})
export class NewProductComponent {
  name: string = "";
  idProduct: string = "";
  price: number = 0;
  loader: boolean = false;

  setName(e: any) {
    this.name = capitalize(e.target.value);
  }

  setIdProduct(e: any) {
    this.idProduct = e.target.value;
  }

  setPrice(e: any) {
    if (e.target.value === "") { 
      this.price = 0;
      return;
    }
    const parsedValue = parseFloat(e.target.value);
    if (isNaN(parsedValue) || parsedValue < 0) {
      this.price = 0;
      return;
    }
    this.price = parsedValue;
  }

  otherPage(where: string) {
    redirect(where);
  }

  async register() {
    if (this.name == null || this.idProduct == null || this.price == null) {
      alert("Preencha todos os campos");
      return;
    }
    this.loader = true;
    const params = {
      name: this.name.toUpperCase(),
      idProduct: this.idProduct,
      price: this.price
    };
    try {
      await axios.post(env.apiUrl + "/product/v1/set", params);
      alert("Produto cadastrado com sucesso!");
      redirect("home");
    } catch {
      alert("Erro interno, talvez o produto já esteja cadastrado.");
    } finally {
      this.loader = false;
    }
  }
}
