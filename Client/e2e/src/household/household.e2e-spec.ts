import {LoginPage} from "../auth/login/login.po";
import {browser, by, ExpectedConditions} from "protractor";
import {HouseholdPage} from "./household.po";
import {DashboardPage} from "../dashboard/dashboard.po";

describe('household page', () => {
  let login: LoginPage;
  let dashboard: DashboardPage
  let household: HouseholdPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    login = new LoginPage();
    dashboard = new DashboardPage();
    household = new HouseholdPage();

    login.login('user1@user.com', 'u');
    browser.wait(ExpectedConditions.urlIs('http://localhost:4200/dashboard/household'), 3000);
  });

  it('should display incomes and expenditures', () => {
    browser.driver.sleep(1000);
    expect(household.getIncomes().count()).toBe(1);
    expect(household.getExpenditures().count()).toBe(1);
  });

  it('should delete transaction', () => {
    const firstIncome = household.getFirstIncome();

    browser.wait(ExpectedConditions.visibilityOf(firstIncome), 3000);
    firstIncome.element(by.buttonText('Delete')).click().then(() => {
      const snackbar = login.getSnackbar();
      browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
      snackbar.getText().then((val) => {
        expect(val).toContain('Transaction deleted successfully');
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
