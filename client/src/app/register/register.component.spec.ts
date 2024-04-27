import { RegisterComponent } from './register.component';

describe('RegisterComponent', () => {
  let component: RegisterComponent;

  beforeEach(() => {
    component = new RegisterComponent();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should initialize variables properly', () => {
    expect(component.password).toEqual('');
    expect(component.secondPassword).toEqual('');
    expect(component.seePassword).toBe(false);
    expect(component.overlay).toBe(false);
    expect(component.adminEmail).toEqual('');
    expect(component.adminPassword).toEqual('');
  });

  it('should set email in lowercase on setAdminEmail', () => {
    component.setAdminEmail({ target: { value: 'Test@Example.com' } });
    expect(component.adminEmail).toEqual('test@example.com');
  });

  it('should convert stateRg to uppercase on setStateRg', () => {
    component.setStateRg({ target: { value: 'sp' } });
    expect(component.stateRg).toEqual('SP');
  });

  it('should set second password on setSecondPassword', () => {
    component.setSecondPassword({ target: { value: 'password' } });
    expect(component.secondPassword).toEqual('password');
  });

  it('should set name on setName', () => {
    component.setName({ target: { value: 'John Doe' } });
    expect(component.name).toEqual('John Doe');
  });

  it('should set overlay to true on checkInformations if all fields are filled', () => {
    component.password = 'password';
    component.secondPassword = 'password';
    component.cpf = '123.456.789-01';
    component.cellphone = '(12) 34567-8901';
    component.email = 'test@example.com';
    component.bday = '1990-01-01';
    component.checkInformations();
    expect(component.overlay).toBe(true);
  });

  it('should not set overlay to true on checkInformations if any field is missing', () => {
    component.password = 'password';
    component.secondPassword = 'password';
    component.cpf = '123.456.789-01';
    component.cellphone = '(12) 34567-8901';
    component.email = 'test@example.com';
    // Missing birthday
    component.checkInformations();
    expect(component.overlay).toBe(false);
  });
});
