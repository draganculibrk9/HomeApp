import {Component, Inject, OnInit} from '@angular/core';
import {SnackbarService} from "../../../services/snackbar.service";
import {TokenService} from "../../../services/token.service";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {AccommodationRow} from "../../../model/accommodation-row";
import {FormControl, FormGroup, Validators} from "@angular/forms";
import {RequestAccommodationRequest} from "../../../proto/generated/services_service_pb";
import {AccommodationRequestMessage} from "../../../proto/generated/accommodation_request_message_pb";
import * as moment from "moment";
import {grpc} from "@improbable-eng/grpc-web";
import {ServicesService} from "../../../proto/generated/services_service_pb_service";
import {environment} from "../../../../environments/environment";
import UnaryOutput = grpc.UnaryOutput;
import {SuccessResponse} from "../../../proto/generated/household_service_pb";
import {DateAdapter} from "@angular/material/core";

@Component({
  selector: 'app-request-accommodation',
  templateUrl: './request-accommodation.component.html',
  styleUrls: ['./request-accommodation.component.css']
})
export class RequestAccommodationComponent implements OnInit {
  minDate = moment().add(1, "day");
  maxDate = moment(this.minDate).add(1, 'year');
  accommodationRequestForm = new FormGroup({
    accommodation: new FormControl(this.data.name),
    requestedFor: new FormControl(this.minDate, [Validators.required])
  });

  constructor(private snackbarService: SnackbarService, public tokenService: TokenService,
              @Inject(MAT_DIALOG_DATA) public data: AccommodationRow, private adapter: DateAdapter<any>,
              public dialogRef: MatDialogRef<RequestAccommodationComponent>) {
    this.dialogRef.disableClose = true;
  }

  ngOnInit() {
    this.adapter.setLocale('sr');
  }

  createAccommodationRequest() {
    if (this.accommodationRequestForm.invalid) {
      this.snackbarService.displayMessage('Some form fields are invalid');
      return;
    }

    const request = new RequestAccommodationRequest();
    request.setOwner(this.tokenService.subject);

    const accommodation_request = new AccommodationRequestMessage();
    accommodation_request.setAccommodation(this.data.id);
    accommodation_request.setFiledOn(moment().unix());
    accommodation_request.setRequestedFor(this.accommodationRequestForm.controls.requestedFor.value.unix());

    request.setAccommodationRequest(accommodation_request);

    grpc.unary(ServicesService.RequestAccommodation, {
      request: request,
      host: environment.servicesServiceHost,
      metadata: {Authorization: `Bearer ${this.tokenService.token}`},
      onEnd: (output: UnaryOutput<SuccessResponse>) => {
        if (output.status === grpc.Code.OK) {
          this.snackbarService.displayMessage('Accommodation requested successfully');
          this.dialogRef.close();
        } else {
          this.snackbarService.displayMessage('Accommodation request failed');
        }
      }
    });
  }
}
