import {LoginPage} from "../auth/login/login.po";
import {browser, ExpectedConditions} from "protractor";
import {UserPage} from "./user.po";
import {DashboardPage} from "../dashboard/dashboard.po";

describe('user', () => {
  let login: LoginPage;
  let user: UserPage;
  let dashboard: DashboardPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    login = new LoginPage();
    user = new UserPage();
    dashboard = new DashboardPage();
  });

  it('should toggle user status', () => {
    login.login('admin@home.app', '@dm1n');
    browser.wait(ExpectedConditions.visibilityOf(user.getFirstRowButton()), 3000);
    user.getFirstRowButton().click().then(() => {
      const snackbar = user.getSnackbar();
      browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
      snackbar.getText().then((val) => {
        expect(val).toContain('User block status toggled successfully');
      });
      user.getFirstRowButton().click().then(() => {
        const snackbar = user.getSnackbar();
        browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
        snackbar.getText().then((val) => {
          expect(val).toContain('User block status toggled successfully');
          dashboard.getLogoutLink().click().then(() => {
            browser.wait(ExpectedConditions.urlIs('http://localhost:4200/login'), 3000);
            expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login');
          });
        });
      });
    });
  });

  afterAll(() => {
    browser.executeScript('window.localStorage.clear();')
  });
});
