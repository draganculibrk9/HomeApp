import {by, element} from "protractor";

export class ServiceAdminPage {
  getServicesTable() {
    return element(by.css('#servicesTable'));
  }

  getServiceTableFirstRow() {
    return element(by.css('#servicesTable tbody tr:first-child'));
  }

  getServiceTableLastRow() {
    return element(by.css('#servicesTable tbody tr:last-child'))
  }

  getAccommodationsTable() {
    return element(by.css('#accommodationsTable'));
  }

  getAccommodationsTableLastRow() {
    return element(by.css('#accommodationsTable tbody tr:last-child'));
  }

  getCreateServiceButton() {
    return element(by.buttonText('Create service'));
  }

  getCreateAccommodationButton() {
    return element(by.buttonText('Create accommodation'));
  }

  getCreateServiceModal() {
    return element(by.xpath('//*[@id="mat-dialog-0"]'));
  }

  getServiceModalName() {
    return element(by.css('#create-service-name'));
  }

  getServiceModalEmail() {
    return element(by.css('input[formControlName=\'email\']'));
  }

  getServiceModalPhone() {
    return element(by.css('input[formControlName=\'phone\']'));
  }

  getServiceModalWebsite() {
    return element(by.css('input[formControlName=\'website\']'));
  }

  getServiceModalCountry() {
    return element(by.css('input[formControlName=\'country\']'));
  }

  getServiceModalCity() {
    return element(by.css('input[formControlName=\'city\']'));
  }

  getServiceModalStreet() {
    return element(by.css('input[formControlName=\'street\']'));
  }

  getServiceModalNumber() {
    return element(by.css('input[formControlName=\'number\']'));
  }

  getServiceModalNextButton() {
    return element(by.buttonText('Next'));
  }

  getServiceModalFinishButton() {
    return element(by.buttonText('Finish'));
  }

  getSnackbar() {
    return element(by.className('mat-simple-snackbar'));
  }

  getAccommodationModal() {
    return element(by.xpath('/html/body/div[2]/div[2]/div/mat-dialog-container'));
  }

  getAccommodationModalNameInput() {
    return element(by.xpath('/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-or-edit-accommodation/div[1]/form/mat-form-field[1]/div/div[1]/div/input'));
  }

  getAccommodationModalPriceInput() {
    return element(by.xpath('/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-or-edit-accommodation/div[1]/form/mat-form-field[2]/div/div[1]/div/input'));
  }

  getAccommodationModalTypeSelect() {
    return element(by.xpath('/html/body/div[2]/div[2]/div/mat-dialog-container/app-create-or-edit-accommodation/div[1]/form/mat-form-field[3]/div/div[1]/div/mat-select'));
  }

  getAccommodationModalSaveButton() {
    return element(by.buttonText('Save'));
  }

  getAccommodationModalCancelButton() {
    return element(by.buttonText('Cancel'));
  }
}
