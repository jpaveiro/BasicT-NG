import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'capitalize',
})
export class CapitalizePipe implements PipeTransform {
  transform(value: string): string {
    return value
      .toLowerCase()
      .split(' ')
      .map((text) => text.charAt(0).toUpperCase() + text.slice(1))
      .join(' ');
  }
}
