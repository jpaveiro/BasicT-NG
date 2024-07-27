import { CapitalizePipe } from './capitalize.pipe';

describe('CapitalizePipe', () => {
  it('Should capitalize a Uppercase text', () => {
    cy.mount('<p>{{ "HII" | capitalize}}</p>', {
      declarations: [CapitalizePipe],
    });

    cy.get('p').contains('Hii');
  });

  it('Should capitalize a Lowercase text', () => {
    cy.mount('<p>{{ "hii" | capitalize }}</p>', {
      declarations: [CapitalizePipe],
    });

    cy.get('p').contains('Hii');
  });
});
