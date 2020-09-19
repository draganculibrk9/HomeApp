import {LoginPage} from "../auth/login/login.po";
import {browser, by, ExpectedConditions} from "protractor";
import {DashboardPage} from "../dashboard/dashboard.po";
import {AccommodationRequestsAdminPage} from "./accommodation-requests-admin.po";

describe('accommodation requests page for service administrator', () => {
  let login: LoginPage;
  let dashboard: DashboardPage;
  let accommodation_request: AccommodationRequestsAdminPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    login = new LoginPage();
    dashboard = new DashboardPage();
    accommodation_request = new AccommodationRequestsAdminPage();
    login.login('serviceadmin1@user.com', 'u');
    browser.wait(ExpectedConditions.visibilityOf(dashboard.getAccommodationRequestsLink()), 3000);
    dashboard.getAccommodationRequestsLink().click().then(() => {
      browser.wait(ExpectedConditions.urlIs('http://localhost:4200/dashboard/accommodation-request'));
    });
  });

  it('should accept accommodation request', () => {
    browser.wait(ExpectedConditions.visibilityOf(accommodation_request.getAccommodationRequestsTable()), 3000);
    browser.driver.sleep(1000);

    accommodation_request.getAccommodationRequestsTable().all(by.css('tbody tr')).count().then((value) => {
      accommodation_request.getAccommodationRequestsTableFirstRow().element(by.buttonText('Accept')).click().then(() => {
        browser.driver.sleep(1000);
        accommodation_request.getAccommodationRequestsTable().all(by.css('tbody tr')).count().then((newValue) => {
          expect(newValue).toBe(value - 1);
        });
      });
    });
  });

  it('should reject accommodation request', () => {
    browser.wait(ExpectedConditions.visibilityOf(accommodation_request.getAccommodationRequestsTable()), 3000);
    browser.driver.sleep(1000);

    accommodation_request.getAccommodationRequestsTable().all(by.css('tbody tr')).count().then((value) => {
      accommodation_request.getAccommodationRequestsTableFirstRow().element(by.buttonText('Reject')).click().then(() => {
        browser.driver.sleep(1000);
        accommodation_request.getAccommodationRequestsTable().all(by.css('tbody tr')).count().then((newValue) => {
          expect(newValue).toBe(value - 1);
        });
      });
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
