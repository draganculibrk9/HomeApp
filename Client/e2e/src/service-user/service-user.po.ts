import {by, element} from "protractor";

export class ServiceUserPage {
  getServicesTable() {
    return element(by.css('#servicesTable'));
  }

  getServiceTableFirstRow() {
    return element(by.css('#servicesTable tbody tr:first-child'));
  }

  getAccommodationsTable() {
    return element(by.css('#accommodationsTable'));
  }

  getNameInput() {
    return element(by.css('input[formControlName=\'name\']'));
  }

  getCityInput() {
    return element(by.css('input[formControlName=\'city\']'));
  }

  getTypeSelect() {
    return element(by.css('mat-select[formControlName=\'type\']'));
  }

  getSearchButton() {
    return element(by.buttonText('Search'));
  }

  getResetButton() {
    return element(by.buttonText('Reset'));
  }

  getServiceDetailsModal() {
    return element(by.css('app-service-details'));
  }

  getServiceDetailsModalBackButton() {
    return element(by.buttonText('Back'));
  }

  getAccommodationsTableFirstRow() {
    return element(by.css('#accommodationsTable tbody tr:first-child'));
  }

  getRequestAccommodationModal() {
    return element(by.css('app-request-accommodation'));
  }
}
