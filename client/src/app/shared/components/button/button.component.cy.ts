import { ButtonComponent } from './button.component';
import { LucideAngularModule, Mail } from 'lucide-angular';

describe('ButtonComponent', () => {
  it('Should mount the button', () => {
    cy.mount('<custom-button child="Text"></custom-button>', {
      declarations: [ButtonComponent],
    });
    cy.get('button').contains('Text');
  });
  it('Should button have the color red', () => {
    cy.mount('<custom-button child="Text" variant="red"></custom-button>', {
      declarations: [ButtonComponent],
    });

    const btn = cy.get('button');
    btn.should('have.css', 'background-color', 'rgb(255, 0, 0)');
    btn.should('have.class', 'btn');
    btn.should('have.class', 'red');
    btn.contains('Text');
  });
  it('Should have the lucide-icon icon and green color', () => {
    cy.mount(
      '<custom-button child="Text" variant="green" icon="mail"></custom-button>',
      {
        declarations: [ButtonComponent],
        imports: [LucideAngularModule.pick({ Mail })],
      }
    );

    const btn = cy.get('button');
    btn.should('have.css', 'background-color', 'rgb(0, 186, 0)');
    btn.should('have.class', 'btn');
    btn.should('have.class', 'green');
    btn.contains('Text');

    cy.get('svg').should('have.class', 'lucide-mail');
  });
});
