import {browser, element, by} from "protractor";

export class RegistrationPage {
  navigateTo() {
    return browser.get('/register');
  }

  getEmailInput() {
    return element(by.css('input[formControlName=email]'));
  }

  getPasswordInput() {
    return element(by.css('input[formControlName=password]'));
  }

  getFirstNameInput() {
    return element(by.css('input[formControlName=firstName]'));
  }

  getLastNameInput() {
    return element(by.css('input[formControlName=lastName]'));
  }

  getPhoneInput() {
    return element(by.css('input[formControlName=phone]'));
  }

  getCountryInput() {
    return element(by.css('input[formControlName=country]'));
  }

  getCityInput() {
    return element(by.css('input[formControlName=city]'));
  }

  getStreetInput() {
    return element(by.css('input[formControlName=street]'));
  }

  getNumberInput() {
    return element(by.css('input[formControlName=number]'));
  }

  getCheckbox() {
    return element(by.xpath('//*[@id="mat-checkbox-1-input"]'));
  }

  getRegisterButton() {
    return element(by.xpath('//*[@id="background"]/mat-card/mat-card-content/form/button[1]'));
  }

  getLoginButton() {
    return element(by.buttonText('Login'));
  }

  getSnackbar() {
    return element(by.className('mat-simple-snackbar'));
  }
}
