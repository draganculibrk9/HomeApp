import {browser, element, by} from "protractor";

export class LoginPage {
  navigateTo() {
    return browser.get('/login');
  }

  getEmailInput() {
    return element(by.xpath('//*[@id="mat-input-0"]'));
  }

  getPasswordInput() {
    return element(by.xpath('//*[@id="mat-input-1"]'));
  }

  getLoginButton() {
    return element(by.xpath('//*[@id="background"]/mat-card/mat-card-content/form/div/button[1]'));
  }

  getRegistrationButton() {
    return element(by.xpath('//*[@id="background"]/mat-card/mat-card-content/form/div/button[2]'));
  }

  getSnackbar() {
    return element(by.className('mat-simple-snackbar'));
  }

  getEmailError() {
    return element(by.xpath('//*[@id="mat-error-0"]'));
  }

  getPasswordError() {
    return element(by.xpath('//*[@id="mat-error-1"]'));
  }

  login(email, password) {
    this.navigateTo();
    this.getEmailInput().sendKeys(email);
    this.getPasswordInput().sendKeys(password);
    this.getLoginButton().click();
  }
}
