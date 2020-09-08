import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {SnackbarService} from "../../../services/snackbar.service";
import {TokenService} from "../../../services/token.service";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {AccommodationType} from "../../../model/accommodation-type.enum";
import {AccommodationResponse, CreateOrEditAccommodationRequest} from "../../../proto/generated/services_service_pb";
import {AccommodationMessage} from "../../../proto/generated/accommodation_message_pb";
import {AccommodationTypePipe} from "../../../pipe/accommodation-type.pipe";
import {grpc} from "@improbable-eng/grpc-web";
import {ServicesService} from "../../../proto/generated/services_service_pb_service";
import {environment} from "../../../../environments/environment";
import UnaryOutput = grpc.UnaryOutput;

@Component({
  selector: 'app-create-or-edit-accommodation',
  templateUrl: './create-or-edit-accommodation.component.html',
  styleUrls: ['./create-or-edit-accommodation.component.css']
})
export class CreateOrEditAccommodationComponent implements OnInit {
  accommodationForm: FormGroup = new FormGroup({
    name: new FormControl('', [Validators.required]),
    price: new FormControl(null, [Validators.required, Validators.min(1)]),
    type: new FormControl(null, [Validators.required]),
    available: new FormControl(true, [Validators.required])
  });

  type = AccommodationType;
  _ = Object;
  private accommodation: AccommodationMessage;
  private changed = false;

  constructor(public dialogRef: MatDialogRef<CreateOrEditAccommodationComponent>,
              @Inject(MAT_DIALOG_DATA) public data: any, private snackbarService: SnackbarService,
              public tokenService: TokenService, private accommodationTypePipe: AccommodationTypePipe) {
    this.dialogRef.disableClose = true;
  }

  ngOnInit(): void {
    if (this.data.accommodation) {
      this.accommodation = new AccommodationMessage();
      this.accommodation.setId(this.data.accommodation.id);
      this.accommodation.setType(CreateOrEditAccommodationComponent.determineAccommodationType(this.data.accommodation.type));
      this.accommodation.setAvailable(this.data.accommodation.available);
      this.accommodation.setName(this.data.accommodation.name);
      this.accommodation.setPrice(this.data.accommodation.price);

      this.setFormValues();
    }
  }

  private setFormValues() {
    this.accommodationForm.controls.type.setValue(this.accommodationTypePipe.transform(this.accommodation.getType()));
    this.accommodationForm.controls.available.setValue(this.accommodation.getAvailable());
    this.accommodationForm.controls.name.setValue(this.accommodation.getName());
    this.accommodationForm.controls.price.setValue(this.accommodation.getPrice());
  }

  createOrEditAccommodation() {
    if (this.accommodationForm.invalid) {
      this.snackbarService.displayMessage('Some form fields are invalid');
      return;
    }
    const request = new CreateOrEditAccommodationRequest();
    request.setServiceId(this.data.service_id);

    const accommodation = new AccommodationMessage();
    accommodation.setPrice(this.accommodationForm.controls.price.value);
    accommodation.setName(this.accommodationForm.controls.name.value);
    accommodation.setAvailable(this.accommodationForm.controls.available.value);
    accommodation.setId(this.accommodation ? this.accommodation.getId() : null);
    accommodation.setType(CreateOrEditAccommodationComponent.determineAccommodationType(this.accommodationForm.controls.type.value));

    request.setAccommodation(accommodation);

    if (this.accommodation) {
      this.editAccommodation(request);
    } else {
      this.createAccommodation(request);
    }
    this.changed = true;
  }

  private static determineAccommodationType(type: AccommodationType) {
    switch (type) {
      case AccommodationType.REPAIRS:
        return AccommodationMessage.AccommodationType.REPAIRS;
      case AccommodationType.HYGIENE:
        return AccommodationMessage.AccommodationType.HYGIENE;
      default:
        return AccommodationMessage.AccommodationType.CATERING;
    }
  }

  private createAccommodation(request: CreateOrEditAccommodationRequest) {
    grpc.unary(ServicesService.CreateAccommodation, {
      request: request,
      host: environment.servicesServiceHost,
      onEnd: (output: UnaryOutput<AccommodationResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.snackbarService.displayMessage('Accommodation created successfully');
          this.accommodation = output.message.getAccommodation();
          this.setFormValues();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      }
    });
  }

  private editAccommodation(request: CreateOrEditAccommodationRequest) {
    grpc.unary(ServicesService.EditAccommodation, {
      request: request,
      host: environment.servicesServiceHost,
      onEnd: (output: UnaryOutput<AccommodationResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.snackbarService.displayMessage('Accommodation edited successfully');
          this.accommodation = output.message.getAccommodation();
          this.setFormValues();
        } else {
          this.snackbarService.displayMessage(output.statusMessage);
        }
      }
    });
  }

  closeDialog() {
    this.dialogRef.close(this.changed);
  }
}
