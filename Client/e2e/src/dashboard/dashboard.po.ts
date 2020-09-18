import {by, element} from "protractor";

export class DashboardPage {
  getUsersLink() {
    return element(by.buttonText('Users'));
  }

  getLogoutLink() {
    return element(by.buttonText('Logout'));
  }

  getHouseholdLink() {
    return element(by.buttonText('Household'));
  }

  getServicesLink() {
    return element(by.buttonText('Services'));
  }

  getAccommodationRequestsLink() {
    return element(by.buttonText('Accommodation Requests'));
  }
}
