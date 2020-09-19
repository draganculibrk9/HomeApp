import {LoginPage} from "../auth/login/login.po";
import {browser, by, element, ExpectedConditions} from "protractor";
import {DashboardPage} from "../dashboard/dashboard.po";
import {ServiceUserPage} from "./service-user.po";
import {shareReplay} from "rxjs/operators";

describe('service page for user', () => {
  let login: LoginPage;
  let dashboard: DashboardPage
  let service: ServiceUserPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    login = new LoginPage();
    dashboard = new DashboardPage();
    service = new ServiceUserPage();
    login.login('user1@user.com', 'u');

    browser.wait(ExpectedConditions.visibilityOf(dashboard.getServicesLink()), 3000);
    dashboard.getServicesLink().click().then(() => {
      browser.wait(ExpectedConditions.urlIs('http://localhost:4200/dashboard/service'), 3000);
    });
  });

  it('should show services', () => {
    browser.driver.sleep(1000);
    service.getServicesTable().all(by.css('tbody tr')).count().then((value) => {
      expect(value).toBe(4);
    });
  });

  it('should show service accommodations', () => {
    browser.driver.sleep(1000);

    expect(service.getServiceTableFirstRow().isDisplayed()).toBeTruthy();
    service.getServiceTableFirstRow().click().then(() => {
      browser.driver.sleep(1000);

      expect(service.getAccommodationsTable().isDisplayed()).toBeTruthy();
      service.getAccommodationsTable().all(by.css('tbody tr')).count().then((value) => {
        expect(value).toBe(2);
      });
    });
  });

  it('should search services', () => {
    browser.driver.sleep(1000);

    expect(service.getServiceTableFirstRow().isDisplayed()).toBeTruthy();

    service.getNameInput().sendKeys('ter');
    service.getCityInput().sendKeys('sing');
    service.getTypeSelect().click().then(() => {
      browser.driver.sleep(1000);

      element(by.css('.mat-option[ng-reflect-value="Catering"]')).click().then(() => {
        service.getSearchButton().click().then(() => {
          browser.driver.sleep(500);

          service.getServicesTable().all(by.css('tbody tr')).count().then((value) => {
            expect(value).toBe(1);

            service.getResetButton().click().then(() => {
              browser.driver.sleep(500);
              service.getServicesTable().all(by.css('tbody tr')).count().then((count) => {
                expect(count).toBe(4);
              });
            });
          });
        });
      });
    });
  });

  it('should display readonly service details', () => {
    browser.driver.sleep(1000);

    expect(service.getServiceTableFirstRow().isDisplayed()).toBeTruthy();

    service.getServiceTableFirstRow().element(by.buttonText('Details')).click().then(() => {
      browser.driver.sleep(500);

      expect(service.getServiceDetailsModal().isDisplayed()).toBeTruthy();
      service.getServiceDetailsModal().all(by.css('input')).each((input) => {
        input.getAttribute('readonly').then((readonly) => {
          expect(readonly).toBe('true');
        });
      });
      browser.driver.sleep(500);
      service.getServiceDetailsModalBackButton().click().then(() => {
        expect(service.getServicesTable().isDisplayed()).toBeTruthy();
      });
    });
  });

  it('should request accommodation', () => {
    browser.driver.sleep(1000);

    expect(service.getServiceTableFirstRow().isDisplayed()).toBeTruthy();

    service.getServiceTableFirstRow().click().then(() => {
      browser.driver.sleep(500);

      expect(service.getAccommodationsTable().isDisplayed()).toBeTruthy();

      service.getAccommodationsTableFirstRow().element(by.buttonText('Request')).click().then(() => {
        browser.driver.sleep(500);

        expect(service.getRequestAccommodationModal().isDisplayed()).toBeTruthy();
        service.getRequestAccommodationModal().element(by.buttonText('Save')).click().then(() => {
          const snackbar = login.getSnackbar();
          browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
          snackbar.getText().then((val) => {
            expect(val).toContain('Accommodation requested successfully');
          });
        });
      });
    });
  });

  afterEach(() => {
    dashboard.getLogoutLink().click().then(() => {
        browser.wait(ExpectedConditions.urlIs('http://localhost:4200/login'), 3000);
        expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login')
      }
    );
    browser.executeScript('window.localStorage.clear();');
  });
});
