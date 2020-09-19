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
    return element(by.cssContainingText('button span', 'Services'));
  }

  getAccommodationRequestsLink() {
    return element(by.cssContainingText('button span', 'Accommodation Requests'));
  }
}
