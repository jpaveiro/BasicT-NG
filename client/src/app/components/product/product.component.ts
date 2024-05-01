import { Component } from '@angular/core';
import { redirect } from '../../util/redirect.util';

@Component({
  selector: 'app-product',
  standalone: true,
  imports: [],
  templateUrl: './product.component.html',
  styleUrl: './product.component.scss'
})
export class ProductComponent {
  goBack() {
    window.history.back();
  }

  redirect(where: string) {
    redirect(where);
  }
}
