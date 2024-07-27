import { InputComponent } from './input.component';

describe('InputComponent', () => {
  it('Should mount custom-input component', () => {
    cy.mount('<custom-input placeholder="text"/>', {
      declarations: [InputComponent],
    });
    cy.get('input').should('have.attr', 'placeholder', 'text');
  });

  it('Should be an input password', () => {
    cy.mount(
      '<custom-input placeholder="text" type="password"></custom-input>',
      {
        declarations: [InputComponent],
      }
    );

    const input = cy.get('input');
    input.should('have.attr', 'placeholder', 'text');
    input.should('have.attr', 'type', 'password');
    cy.get('.app-input').should(
      'have.css',
      'border',
      '2px solid rgb(186, 186, 186)'
    );
  });
  it('Should have an label', () => {
    cy.mount('<custom-input placeholder="Email" label="Email: " />', {
      declarations: [InputComponent],
    });

    cy.get('label').contains('Email: ');
    cy.get('input').should('have.attr', 'placeholder', 'Email');
  });
});
