import {browser, ExpectedConditions} from 'protractor';
import {RegistrationPage} from "./registration.po";

describe('registration page', () => {
  let page: RegistrationPage;

  beforeAll(() => {
    browser.driver.manage().window().maximize();
  });

  beforeEach(() => {
    page = new RegistrationPage();
    page.navigateTo();
  });

  it('should go to login page', () => {
    expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/register');
    page.getLoginButton().click().then(() => {
      expect(browser.getCurrentUrl()).toMatch('http://localhost:4200/login');
    });
  });

  it('should not register if email is taken', () => {
    page.getEmailInput().sendKeys('user1@user.com');
    page.getPasswordInput().sendKeys('password');
    page.getFirstNameInput().sendKeys('First Name');
    page.getLastNameInput().sendKeys('Last Name');
    page.getPhoneInput().sendKeys('+381 649974343');
    page.getCountryInput().sendKeys('srb');
    page.getCityInput().sendKeys('city');
    page.getStreetInput().sendKeys('street');
    page.getNumberInput().sendKeys('2');
    page.getRegisterButton().click().then(() => {
      const snackbar = page.getSnackbar();
      browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
      snackbar.getText().then((val) => {
        expect(val).toContain('User with email \'user1@user.com\' already exists');
      });
    });
  });

  it('should register', () => {
    page.getEmailInput().sendKeys('newUser@user.com');
    page.getPasswordInput().sendKeys('password');
    page.getFirstNameInput().sendKeys('First Name');
    page.getLastNameInput().sendKeys('Last Name');
    page.getPhoneInput().sendKeys('+381 649974343');
    page.getCountryInput().sendKeys('srb');
    page.getCityInput().sendKeys('city');
    page.getStreetInput().sendKeys('street');
    page.getNumberInput().sendKeys('2');
    page.getRegisterButton().click().then(() => {
      const snackbar = page.getSnackbar();
      browser.wait(ExpectedConditions.visibilityOf(snackbar), 3000);
      snackbar.getText().then((val) => {
        expect(val).toContain('Registration successful');
      });
    });
  });

  afterAll(() => {
    page.navigateTo();
    browser.driver.sleep(1000);
    browser.waitForAngular();
  });

});
