import {LoginPage} from "../login/login.po";
import {browser, ExpectedConditions} from "protractor";
import {DashboardPage} from "./dashboard.po";

describe('dashboard', () => {
  let login: LoginPage;
  let dashboard: DashboardPage

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    login = new LoginPage();
    dashboard = new DashboardPage();
  });

  describe('dashboard for system administrator', () => {
    it('should contain system administrator links', () => {
      login.login('admin@home.app', '@dm1n');
      browser.wait(ExpectedConditions.urlIs('http://localhost:4200/dashboard/user'), 3000);
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/dashboard/user');
      expect(dashboard.getUsersLink().isDisplayed()).toBeTruthy();
      expect(dashboard.getLogoutLink().isDisplayed()).toBeTruthy();
      dashboard.getLogoutLink().click().then(() => {
          browser.wait(ExpectedConditions.urlIs('http://localhost:4200/login'), 3000);
          expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login')
        }
      );
    });
  });

  describe('dashboard for service administrator', () => {
    it('should contain service administrator links', () => {
      login.login('serviceadmin1@user.com', 'u');
      browser.wait(ExpectedConditions.urlIs('http://localhost:4200/dashboard/service'), 3000);
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/dashboard/service');
      expect(dashboard.getServicesLink().isDisplayed()).toBeTruthy();
      expect(dashboard.getAccommodationRequestsLink().isDisplayed()).toBeTruthy();
      expect(dashboard.getLogoutLink().isDisplayed()).toBeTruthy();
      dashboard.getLogoutLink().click().then(() => {
          browser.wait(ExpectedConditions.urlIs('http://localhost:4200/login'), 3000);
          expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login')
        }
      );
    });
  });

  describe('dashboard for user', () => {
    it('should contain user links', () => {
      login.login('user1@user.com', 'u');
      browser.wait(ExpectedConditions.urlIs('http://localhost:4200/dashboard/household'), 3000);
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/dashboard/household');
      expect(dashboard.getHouseholdLink().isDisplayed()).toBeTruthy();
      expect(dashboard.getServicesLink().isDisplayed()).toBeTruthy();
      expect(dashboard.getAccommodationRequestsLink().isDisplayed()).toBeTruthy();
      expect(dashboard.getLogoutLink().isDisplayed()).toBeTruthy();
      dashboard.getLogoutLink().click().then(() => {
          browser.wait(ExpectedConditions.urlIs('http://localhost:4200/login'), 3000);
          expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login')
        }
      );
    });
  });

  afterAll(() => {
    browser.executeScript('window.localStorage.clear();')
  });
});
