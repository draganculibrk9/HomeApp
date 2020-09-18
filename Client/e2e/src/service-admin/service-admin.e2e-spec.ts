import {LoginPage} from "../auth/login/login.po";
import {browser, by, element, ExpectedConditions} from "protractor";
import {DashboardPage} from "../dashboard/dashboard.po";
import {ServiceAdminPage} from "./service-admin.po";

describe('service page for service administrator', () => {
  let login: LoginPage;
  let dashboard: DashboardPage
  let service: ServiceAdminPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    login = new LoginPage();
    dashboard = new DashboardPage();
    service = new ServiceAdminPage();
    login.login('serviceadmin1@user.com', 'u');
  });

  it('should show services and accommodations', () => {
    browser.wait(ExpectedConditions.visibilityOf(service.getServicesTable()), 3000);
    browser.driver.sleep(1000);
    expect(service.getServiceTableFirstRow().isDisplayed()).toBeTruthy();
    service.getServiceTableFirstRow().click().then(() => {
      browser.wait(ExpectedConditions.visibilityOf(service.getAccommodationsTable()), 3000);
      expect(service.getAccommodationsTable().isDisplayed()).toBeTruthy();
    });
  });

  it('should create service', () => {
    browser.wait(ExpectedConditions.visibilityOf(service.getServiceTableFirstRow()), 3000);
    let count: number;
    service.getServicesTable().all(by.css('tbody tr')).count().then((value) => {
      count = value;
    });
    service.getCreateServiceButton().click().then(() => {
      browser.wait(ExpectedConditions.visibilityOf(service.getCreateServiceModal()), 3000);
      browser.driver.sleep(1000);
      service.getServiceModalName().sendKeys('zz service');
      service.getServiceModalEmail().sendKeys('newservice@email.com');
      service.getServiceModalPhone().sendKeys('+395 049964343');
      service.getServiceModalWebsite().sendKeys('www.newservice.com');
      service.getServiceModalCountry().sendKeys('country');
      service.getServiceModalCity().sendKeys('city');
      service.getServiceModalStreet().sendKeys('street');
      service.getServiceModalNumber().sendKeys('2');

      service.getServiceModalNextButton().click().then(() => {
        const snackbar = service.getSnackbar();
        browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
        snackbar.getText().then((val) => {
          expect(val).toContain('Service created successfully');

          browser.wait(ExpectedConditions.visibilityOf(service.getServiceModalFinishButton()), 3000);
          service.getServiceModalFinishButton().click().then(() => {
            browser.wait(ExpectedConditions.visibilityOf(service.getServiceTableFirstRow()), 3000);
            expect(service.getServicesTable().all(by.css('tbody tr')).count()).toBe(count + 1);
          });
        });
      });
    });
  });

  it('should create accommodation', () => {
    browser.wait(ExpectedConditions.visibilityOf(service.getServiceTableLastRow()), 3000);

    service.getServiceTableLastRow().click().then(() => {
      browser.wait(ExpectedConditions.visibilityOf(service.getAccommodationsTable()), 3000);
      expect(service.getAccommodationsTable().isDisplayed()).toBeTruthy();
      service.getAccommodationsTable().all(by.css('tbody tr')).count().then((value) => {
        expect(value).toBe(1); // no data row

        service.getCreateAccommodationButton().click().then(() => {
          browser.wait(ExpectedConditions.visibilityOf(service.getAccommodationModal()), 3000);
          service.getAccommodationModalNameInput().sendKeys('new accommodation');
          service.getAccommodationModalPriceInput().sendKeys('3000.0');
          service.getAccommodationModalTypeSelect().click().then(() => {
            browser.driver.sleep(1000);
            element(by.css('.mat-option[ng-reflect-value="Repairs"]')).click()
              .then(() => {
                service.getAccommodationModalSaveButton().click().then(() => {
                  const snackbar = service.getSnackbar();
                  browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
                  snackbar.getText().then((val) => {
                    expect(val).toContain('Accommodation created successfully');

                    browser.wait(ExpectedConditions.visibilityOf(service.getAccommodationModalCancelButton()), 3000);
                    service.getAccommodationModalCancelButton().click().then(() => {
                      browser.wait(ExpectedConditions.visibilityOf(service.getAccommodationsTable()), 3000);
                      expect(service.getAccommodationsTable().all(by.css('tbody tr')).count()).toBe(1);
                    });
                  });
                });
              });
          });
        });
      });
    });
  });

  it('should delete accommodation', () => {
    browser.wait(ExpectedConditions.visibilityOf(service.getServiceTableLastRow()), 3000);

    service.getServiceTableLastRow().click().then(() => {
      browser.wait(ExpectedConditions.visibilityOf(service.getAccommodationsTableLastRow()), 3000);

      service.getAccommodationsTableLastRow().element(by.buttonText('Delete')).click().then(() => {
        const snackbar = service.getSnackbar();
        browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
        snackbar.getText().then((val) => {
          expect(val).toContain('Accommodation deleted successfully');
        });
      });
    });
  });

  it('should delete service', () => {
    browser.wait(ExpectedConditions.visibilityOf(service.getServicesTable()), 3000);
    browser.wait(ExpectedConditions.visibilityOf(service.getServiceTableLastRow()), 3000);

    service.getServiceTableLastRow().element(by.buttonText('Delete')).click().then(() => {
      const snackbar = service.getSnackbar();
      browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
      snackbar.getText().then((val) => {
        expect(val).toContain('Service deleted successfully');
      });
    });
  });

  afterEach(() => {
    browser.executeScript('window.localStorage.clear();');
  });

});
