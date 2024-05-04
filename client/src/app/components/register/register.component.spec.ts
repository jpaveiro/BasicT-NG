import { TestBed } from "@angular/core/testing";
import { RegisterComponent } from "./register.component"
import { CookieService } from "ngx-cookie-service";


describe('RegisterComponent', () => {
  let registerComponent: RegisterComponent;
  let cookieService: CookieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RegisterComponent, CookieService]
    });

    registerComponent = TestBed.inject(RegisterComponent);
    cookieService = TestBed.inject(CookieService);

    cookieService.deleteAll();
  });

  it('should be created', () => {
    expect(registerComponent).toBeTruthy();
  });

  it('should block access', () => {
    expect(registerComponent.isLogged()).toBe(false);
  });

  it('should allow access', () => {
    cookieService.set('basict:user-token', '12345678');

    expect(registerComponent.isLogged()).toBe(true);
  })

  it('should convert email and stateRg case', () => {
    const email: string = 'ADMIN@ADMIN.org';
    const stateRg: string = 'sSp/Sp';

    registerComponent.handleEmail({target: {value: email}})
    registerComponent.setStateRg({target: {value: stateRg}})

    expect(registerComponent.email).toBe('admin@admin.org');
    expect(registerComponent.stateRg).toBe('SSP/SP')
  })

  it('should return error from empty fields or diferent passwords', () => {
    expect(registerComponent.checkInformations()).toBe(false);
  });
})