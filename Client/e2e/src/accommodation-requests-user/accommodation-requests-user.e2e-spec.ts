import {LoginPage} from "../auth/login/login.po";
import {browser, by, ExpectedConditions} from "protractor";
import {DashboardPage} from "../dashboard/dashboard.po";
import {AccommodationRequestsUserPage} from "./accommodation-requests-user.po";

describe('accommodation requests page for user', () => {
  let login: LoginPage;
  let dashboard: DashboardPage;
  let accommodation_request: AccommodationRequestsUserPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    login = new LoginPage();
    dashboard = new DashboardPage();
    accommodation_request = new AccommodationRequestsUserPage();
    login.login('user1@user.com', 'u');
    browser.wait(ExpectedConditions.visibilityOf(dashboard.getAccommodationRequestsLink()), 3000);
    dashboard.getAccommodationRequestsLink().click().then(() => {
      browser.wait(ExpectedConditions.urlIs('http://localhost:4200/dashboard/accommodation-request'));
    });
  });

  it('should show accommodation requests', () => {
    browser.driver.sleep(1000);
    expect(accommodation_request.getAccommodationRequestsTable().isDisplayed()).toBeTruthy();


    accommodation_request.getAccommodationRequestsTable().all(by.css('tbody tr')).count().then((value) => {
      expect(value).toBe(4);
    });
  });

  afterEach(() => {
    dashboard.getLogoutLink().click().then(() => {
        browser.wait(ExpectedConditions.urlIs('http://localhost:4200/login'), 3000);
        expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login');
      }
    );
    browser.executeScript('window.localStorage.clear();');
  });
});
