import {by, element} from "protractor";

export class UserPage {
  getFirstRowButton() {
    return element(by.xpath('//*[@id="parent"]/table/tbody/tr[1]/td[5]/button'));
  }

  getSnackbar() {
    return element(by.className('mat-simple-snackbar'));
  }
}
