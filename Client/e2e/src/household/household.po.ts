import {by, element} from "protractor";

export class HouseholdPage {
  getIncomes() {
    return element.all(by.xpath('//app-transaction[.//mat-icon[@ng-reflect-message = \'Income\']]'));
  }

  getFirstIncome() {
    return this.getIncomes().first();
  }

  getEditTransactionModal() {
    return element(by.css('app-edit-transaction'));
  }

  getExpenditures() {
    return element.all(by.xpath('//app-transaction[.//mat-icon[@ng-reflect-message = \'Expenditure\']]'));
  }
}
