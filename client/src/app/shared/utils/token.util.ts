import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root',
})
export class TokenUtil {
  generate(): string {
    return 'b' + Math.random().toString(36).substring(2, 15);
  }
}
