import { TestBed } from "@angular/core/testing";
import { AdminGuard } from "./admin.guard";
import { CookieService } from "ngx-cookie-service";

describe('AdminGuard', () => {
  let guard: AdminGuard;
  let cookieService: CookieService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [AdminGuard, CookieService]
    });

    guard = TestBed.inject(AdminGuard);
    cookieService = TestBed.inject(CookieService);

    cookieService.deleteAll();
  });

  it('should be created', () => {
    expect(guard).toBeTruthy();
  });

  it ('should block normal user login', () => {
    expect(guard.canActivate()).toBe(false);
  });

  it('should allow access when super user token is present', () => {
    cookieService.set("basict:super-user-token", "12345678");

    const canActivate = guard.canActivate();
    expect(canActivate).toBe(true);

    cookieService.delete("basict:super-user-token");
  });
});
