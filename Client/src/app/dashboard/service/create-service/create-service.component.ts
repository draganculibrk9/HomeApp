import {Component, OnInit, ViewChild} from '@angular/core';
import {MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../services/snackbar.service";
import {TokenService} from "../../../services/token.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AccommodationType} from "../../../model/accommodation-type.enum";
import {ServiceMessage} from "../../../proto/generated/service_message_pb";
import {
  AccommodationResponse,
  CreateOrEditAccommodationRequest,
  CreateOrEditServiceRequest, ServiceRequest,
  ServiceResponse
} from "../../../proto/generated/services_service_pb";
import {ContactMessage} from "../../../proto/generated/contact_message_pb";
import {AddressMessage} from "../../../proto/generated/address_message_pb";
import {grpc} from "@improbable-eng/grpc-web";
import {ServicesService} from "../../../proto/generated/services_service_pb_service";
import UnaryOutput = grpc.UnaryOutput;
import {AccommodationMessage} from "../../../proto/generated/accommodation_message_pb";
import {environment} from "../../../../environments/environment";
import {MatTableDataSource} from "@angular/material/table";
import {AccommodationRow} from "../../../model/accommodation-row";
import {MatPaginator} from "@angular/material/paginator";
import {MatSort} from "@angular/material/sort";
import {AccommodationTypePipe} from "../../../pipe/accommodation-type.pipe";

@Component({
  selector: 'app-create-service',
  templateUrl: './create-service.component.html',
  styleUrls: ['./create-service.component.css']
})
export class CreateServiceComponent implements OnInit {
  serviceForm: FormGroup;
  accommodationForm: FormGroup;
  type = AccommodationType;
  _ = Object;
  private service: ServiceMessage;
  dataSource: MatTableDataSource<AccommodationRow>;
  displayedColumns: string[] = ['id', 'name', 'type', 'price', 'available'];

  @ViewChild(MatPaginator, {static: true}) paginator: MatPaginator;
  @ViewChild(MatSort, {static: true}) sort: MatSort;

  constructor(public dialogRef: MatDialogRef<CreateServiceComponent>, private snackbarService: SnackbarService,
              public tokenService: TokenService, private accommodationTypePipe: AccommodationTypePipe) {
    this.dialogRef.disableClose = true;
  }

  ngOnInit(): void {
    this.initializeForm();
  }

  private initializeForm() {
    this.serviceForm = new FormGroup({
      name: new FormControl('',
        [
          Validators.required
        ]),
      email: new FormControl('',
        [
          Validators.required,
          Validators.pattern('^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$')
        ]),
      phone: new FormControl('',
        [
          Validators.required,
          Validators.pattern('^\\+[0-9]{3}\\s[0-9]{8,9}$')
        ]),
      website: new FormControl('',
        [
          Validators.required,
          Validators.pattern('(https?://)?([\\da-z.-]+)\\.([a-z.]{2,6})[/\\w .-]*/?')
        ]),
      country: new FormControl('',
        [
          Validators.required
        ]),
      city: new FormControl('',
        [
          Validators.required
        ]),
      street: new FormControl('',
        [
          Validators.required
        ]),
      number: new FormControl(null,
        [
          Validators.required,
          Validators.min(1)
        ])
    });

    this.accommodationForm = new FormGroup({
      name: new FormControl('', [Validators.required]),
      price: new FormControl(null, [Validators.required, Validators.min(1)]),
      type: new FormControl(null, [Validators.required]),
      available: new FormControl(true, [Validators.required])
    });
  }

  createAccommodation() {
    if (this.service === undefined) {
      this.snackbarService.displayMessage('Service not created');
      return
    } else if (this.accommodationForm.invalid) {
      this.snackbarService.displayMessage('Some fields are invalid');
      return;
    }

    const request = new CreateOrEditAccommodationRequest();
    request.setServiceId(this.service.getId());

    const accommodation = new AccommodationMessage();
    accommodation.setAvailable(this.accommodationForm.controls.available.value);
    accommodation.setName(this.accommodationForm.controls.name.value);
    accommodation.setPrice(this.accommodationForm.controls.price.value)

    switch (this.accommodationForm.controls.type.value) {
      case AccommodationType.CATERING:
        accommodation.setType(AccommodationMessage.AccommodationType.CATERING);
        break;
      case AccommodationType.HYGIENE:
        accommodation.setType(AccommodationMessage.AccommodationType.HYGIENE);
        break;
      default:
        accommodation.setType(AccommodationMessage.AccommodationType.REPAIRS);
    }
    request.setAccommodation(accommodation);

    grpc.unary(ServicesService.CreateAccommodation, {
      request: request,
      host: environment.servicesServiceHost,
      onEnd: (output: UnaryOutput<AccommodationResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.snackbarService.displayMessage('Accommodation created successfully');
          this.getAccommodations();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      }
    });

  }

  createService() {
    if (this.serviceForm.invalid) {
      this.snackbarService.displayMessage('Some fields are invalid');
      return;
    }

    const request = new CreateOrEditServiceRequest();
    const message = new ServiceMessage();

    message.setAdministrator(this.tokenService.subject);
    message.setName(this.serviceForm.controls.name.value);

    const contact = new ContactMessage();
    contact.setEmail(this.serviceForm.controls.email.value);
    contact.setPhone(this.serviceForm.controls.phone.value);
    contact.setWebsite(this.serviceForm.controls.website.value);

    const address = new AddressMessage();
    address.setCity(this.serviceForm.controls.city.value);
    address.setCountry(this.serviceForm.controls.country.value);
    address.setNumber(this.serviceForm.controls.number.value);
    address.setStreet(this.serviceForm.controls.street.value);

    contact.setAddress(address);
    message.setContact(contact);
    request.setService(message);

    grpc.unary(ServicesService.CreateService, {
      request: request,
      host: environment.servicesServiceHost,
      onEnd: (output: UnaryOutput<ServiceResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.service = output.message.getService();
          this.snackbarService.displayMessage('Service created successfully');
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      }
    });
  }

  closeModal() {
    this.dialogRef.close(this.service !== undefined);
  }

  private getAccommodations() {
    const request = new ServiceRequest();
    request.setId(this.service.getId());

    const accommodations: AccommodationRow[] = [];

    grpc.invoke(ServicesService.GetAccommodations, {
      request: request,
      host: environment.servicesServiceHost,
      onMessage: (res: AccommodationResponse) => {
        const accommodation = res.getAccommodation();
        accommodations.push({
          available: accommodation.getAvailable(),
          id: accommodation.getId(),
          name: accommodation.getName(),
          price: accommodation.getPrice(),
          type: this.accommodationTypePipe.transform(accommodation.getType())
        });
      },
      onEnd: (code: grpc.Code, message: string) => {
        if (code === grpc.Code.OK) {
          this.dataSource = new MatTableDataSource<AccommodationRow>(accommodations);
          this.dataSource.sort = this.sort;
          this.dataSource.paginator = this.paginator;
        } else {
          this.snackbarService.displayMessage(message);
        }
      }
    });
  }
}
