import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../services/snackbar.service";
import {
  CreateOrEditServiceRequest,
  ServiceRequest,
  ServiceResponse
} from "../../../proto/generated/services_service_pb";
import {ServicesService} from "../../../proto/generated/services_service_pb_service";
import {grpc} from "@improbable-eng/grpc-web";
import UnaryOutput = grpc.UnaryOutput;
import {ServiceMessage} from "../../../proto/generated/service_message_pb";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {TokenService} from "../../../services/token.service";
import {environment} from "../../../../environments/environment";
import {ContactMessage} from "../../../proto/generated/contact_message_pb";
import {AddressMessage} from "../../../proto/generated/address_message_pb";

@Component({
  selector: 'app-service-details',
  templateUrl: './service-details.component.html',
  styleUrls: ['./service-details.component.css']
})
export class ServiceDetailsComponent implements OnInit {
  service: ServiceMessage;
  serviceForm: FormGroup;
  edited = false;

  constructor(public dialogRef: MatDialogRef<ServiceDetailsComponent>, @Inject(MAT_DIALOG_DATA) public service_id: number,
              private snackbarService: SnackbarService, public tokenService: TokenService) {
    this.dialogRef.disableClose = true;
  }

  ngOnInit(): void {
    this.getService();
  }

  private initializeForm() {
    this.serviceForm = new FormGroup({
      name: new FormControl(
        this.service.getName(),
        [
          Validators.required
        ]),
      email: new FormControl(
        this.service.getContact().getEmail(),
        [
          Validators.required,
          Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')
        ]),
      phone: new FormControl(
        this.service.getContact().getPhone(),
        [
          Validators.required
        ]),
      website: new FormControl(
        this.service.getContact().getWebsite(),
        [
          Validators.required,
          Validators.pattern('(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?')
        ]),
      country: new FormControl(
        this.service.getContact().getAddress().getCountry(),
        [
          Validators.required
        ]),
      city: new FormControl(
        this.service.getContact().getAddress().getCity(),
        [
          Validators.required
        ]),
      street: new FormControl(
        this.service.getContact().getAddress().getStreet(),
        [
          Validators.required
        ]),
      number: new FormControl(this.service.getContact().getAddress().getNumber(),
        [
          Validators.required
        ])
    });
  }

  private getService() {
    const request = new ServiceRequest();
    request.setId(this.service_id);

    grpc.unary(ServicesService.GetService, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onEnd: (output: UnaryOutput<ServiceResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.service = output.message.getService();
          this.initializeForm();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      },
    });
  }

  saveService() {
    if (this.serviceForm.invalid) {
      this.snackbarService.displayMessage('Some fields are invalid');
      return;
    }

    const request = new CreateOrEditServiceRequest();

    const service = new ServiceMessage();
    service.setName(this.serviceForm.controls.name.value);
    service.setId(this.service.getId());
    service.setAdministrator(this.service.getAdministrator());

    const contact = new ContactMessage();
    contact.setId(this.service.getContact().getId());
    contact.setWebsite(this.serviceForm.controls.website.value);
    contact.setPhone(this.serviceForm.controls.phone.value)
    contact.setEmail(this.serviceForm.controls.email.value)

    const address = new AddressMessage();
    address.setId(this.service.getContact().getAddress().getId());
    address.setStreet(this.serviceForm.controls.street.value);
    address.setNumber(this.serviceForm.controls.number.value);
    address.setCountry(this.serviceForm.controls.country.value);
    address.setCity(this.serviceForm.controls.city.value);

    contact.setAddress(address);
    service.setContact(contact);
    request.setService(service);

    grpc.unary(ServicesService.EditService, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onEnd: (output: UnaryOutput<ServiceResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.service = output.message.getService();
          this.edited = true;
          this.snackbarService.displayMessage('Service edited successfully');
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      },
    });
  }

  closeDialog() {
    this.dialogRef.close(this.edited);
  }
}
