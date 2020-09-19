import {by, element} from "protractor";

export class AccommodationRequestsAdminPage {
  getAccommodationRequestsTable() {
    return element(by.css('#accommodation-requests-table'));
  }

  getAccommodationRequestsTableFirstRow() {
    return element(by.css('table tbody tr:first-child'));
  }
}
