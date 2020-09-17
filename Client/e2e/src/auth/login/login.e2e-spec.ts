import {browser, ExpectedConditions} from 'protractor';
import {LoginPage} from './login.po';

describe('login page', () => {
  let page: LoginPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    page = new LoginPage();
    page.navigateTo();
  });

  it('should not login with wrong credentials', () => {
    page.getEmailInput().sendKeys('JennifferHooker@example.com');
    expect(page.getLoginButton().isEnabled()).toBe(false);
    page.getPasswordInput().sendKeys('111');
    expect(page.getLoginButton().isEnabled()).toBe(true);
    page.getLoginButton().click().then(() => {
      const snackbar = page.getSnackbar();
      browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
      snackbar.getText().then((val) => {
        expect(val).toContain('Bad email or password');
      });
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login');
    });
  });

  it('should not login for blocked user', () => {
    page.getEmailInput().sendKeys('user3@user.com');
    expect(page.getLoginButton().isEnabled()).toBe(false);
    page.getPasswordInput().sendKeys('u');
    expect(page.getLoginButton().isEnabled()).toBe(true);
    page.getLoginButton().click().then(() => {
      const snackbar = page.getSnackbar();
      browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
      snackbar.getText().then((val) => {
        expect(val).toContain('Bad email or password');
      });
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login');
    });
  });

  it('should show error message on failed email validation', () => {
    page.getEmailInput().click();
    page.getEmailInput().sendKeys('JennifferHookerexample.com');
    expect(page.getLoginButton().isEnabled()).toBe(false);
    expect(page.getEmailError().isDisplayed()).toBe(true);
  });

  it('should show error message on failed password validation', () => {
    page.getPasswordInput().click();
    page.getEmailInput().click();
    expect(page.getLoginButton().isEnabled()).toBe(false);
    expect(page.getPasswordError().isDisplayed()).toBe(true);
  });

  it('should login for regular user', () => {
    page.getEmailInput().sendKeys('user1@user.com');
    expect(page.getLoginButton().isEnabled()).toBe(false);
    page.getPasswordInput().sendKeys('u');
    expect(page.getLoginButton().isEnabled()).toBe(true);
    page.getLoginButton().click().then(() => {
      browser.driver.sleep(1000);
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/dashboard/household');
      const valLocalStorage = browser.executeScript('return window.localStorage.getItem(\'token\');');
      expect(valLocalStorage).not.toBeNull();
    });
  });

  it('should login for service administrator', () => {
    page.getEmailInput().sendKeys('serviceadmin1@user.com');
    expect(page.getLoginButton().isEnabled()).toBe(false);
    page.getPasswordInput().sendKeys('u');
    expect(page.getLoginButton().isEnabled()).toBe(true);
    page.getLoginButton().click().then(() => {
      browser.driver.sleep(1000);
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/dashboard/service');
      const valLocalStorage = browser.executeScript('return window.localStorage.getItem(\'token\');');
      expect(valLocalStorage).not.toBeNull();
    });
  });

  it('should login for system administrator', () => {
    page.getEmailInput().sendKeys('admin@home.app');
    expect(page.getLoginButton().isEnabled()).toBe(false);
    page.getPasswordInput().sendKeys('@dm1n');
    expect(page.getLoginButton().isEnabled()).toBe(true);
    page.getLoginButton().click().then(() => {
      browser.driver.sleep(1000);
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/dashboard/user');
      const valLocalStorage = browser.executeScript('return window.localStorage.getItem(\'token\');');
      expect(valLocalStorage).not.toBeNull();
    });
  });

  afterAll(() => {
    page.navigateTo();
    browser.driver.sleep(1000);
    browser.waitForAngular();
    browser.executeScript('window.localStorage.clear();')
  });

});
