import {by, element} from "protractor";

export class AccommodationRequestsUserPage {
  getAccommodationRequestsTable() {
    return element(by.css('#accommodation-requests-table'));
  }
}
